package com.example.CryptoParser.CryptoService;

import com.example.CryptoParser.CryptoRepo.Cryptocurrency;
import com.example.CryptoParser.CryptoRepo.CryptoStorage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CryptoService {

    private final CryptoStorage cryptoStorage;

    public CryptoService(CryptoStorage cryptoStorage) {
        this.cryptoStorage = cryptoStorage;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void scrapeDataOnStartup() {
        try {
            Document doc = Jsoup.connect("https://coinmarketcap.com/")
                    .userAgent("Mozilla/5.0")
                    .get();

            Elements rows = doc.select("table tbody tr");

            for (int i = 0; i < Math.min(10, rows.size()); i++) {
                Element row = rows.get(i);
                Elements columns = row.select("td");

                if (columns.size() < 9) continue;

                // 1. Извлекаем Имя и Символ
                String nameInfo = columns.get(2).select("p[font-weight=semibold]").text();
                String symbol = columns.get(2).select("p[color=text3]").text();

                if (nameInfo.isEmpty()) {
                    // Запасной вариант парсинга, если селекторы выше не сработали
                    nameInfo = columns.get(2).text().split(" ")[0];
                }

                // 2. Очищаем строки от символов $ и запятых для перевода в BigDecimal
                String priceStr = columns.get(3).text().replaceAll("[$,]", "");
                String marketCapStr = columns.get(7).text().replaceAll("[$,]", "");
                // Для supply убираем всё кроме цифр
                String supplyStr = columns.get(8).text().replaceAll("[^0-9]", "");

                // 3. Создаем объекты BigDecimal (они точнее для цен)
                BigDecimal priceUSD = new BigDecimal(priceStr);
                BigDecimal marketCapUSD = new BigDecimal(marketCapStr);
                int circulatingSupply = Integer.parseInt(supplyStr);

                // 4. Создаем экземпляр твоего класса
                // Конструктор сам вызовет validate-методы
                Cryptocurrency crypto = new Cryptocurrency(
                        i + 1,
                        nameInfo,
                        symbol,
                        nameInfo.toLowerCase(),
                        circulatingSupply,
                        priceUSD,
                        marketCapUSD
                );

                cryptoStorage.save(crypto);
                System.out.println("Загружено в базу: " + crypto.getName());
            }

        } catch (Exception e) {
            System.err.println("Ошибка парсинга: " + e.getMessage());
        }
    }
}
