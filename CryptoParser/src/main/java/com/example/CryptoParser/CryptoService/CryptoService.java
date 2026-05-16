package com.example.CryptoParser.CryptoService;

import com.example.CryptoParser.CryptoRepo.Cryptocurrency;
import com.example.CryptoParser.CryptoRepo.CryptoStorage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import java.util.regex.*;

import java.math.BigDecimal;

@Service
@Order(1)
public class CryptoService implements CommandLineRunner {

    private final CryptoStorage cryptoStorage;

    public CryptoService(CryptoStorage cryptoStorage) {
        this.cryptoStorage = cryptoStorage;
    }

    @Override
    public void run(String... args) throws Exception {
        PasrseData();
    }

    public void PasrseData() {
        System.out.println("Загрузка данных с coinmarketcap.com...");
        try {
            Document doc = Jsoup.connect("https://coinmarketcap.com/")
                    .userAgent("Mozilla/5.0")
                    .timeout(20000)
                    .get();

            Elements rows = doc.select("table tbody tr");

            if (rows.isEmpty()) {
                System.err.println("Не удалось найти таблицу с криптовалютами на странице");
                return;
            }

            int successCount = 0;
            int errorCount = 0;

            for (int i = 0; i < Math.min(15, rows.size()); i++) {
                try {
                    Element row = rows.get(i);
                    Elements columns = row.select("td");

                    if (columns.size() < 9) {
                        System.err.println("Строка " + (i + 1) + ": недостаточно колонок");
                        errorCount++;
                        continue;
                    }

                    String idStr = columns.get(1).text().trim();
                    long cryptoId = 0L;
                    try {
                        cryptoId = Long.parseLong(idStr);
                    } catch (NumberFormatException e) {
                        cryptoId = 0L;
                    }

                    String nameInfo = columns.get(2).select("p[font-weight=semibold]").text();
                    String symbol = columns.get(2).select("p[color=text3]").text();

                    if (nameInfo.isEmpty()) {
                        String fullText = columns.get(2).text().trim();
                        String[] parts = fullText.split("\\s+");

                        if (parts.length >= 2) {
                            symbol = parts[parts.length - 1];
                            nameInfo = fullText.substring(0, fullText.length() - symbol.length()).trim();
                        }
                    }

                    if (nameInfo.isEmpty()) {
                        System.err.println("Строка " + (i + 1) + ": не удалось извлечь название");
                        errorCount++;
                        continue;
                    }

                    String priceStr = columns.get(3).text().replaceAll("[$,]", "").trim();
                    String marketCapStr = columns.get(7).text().replaceAll("[$,]", "").trim();
                    String supplyStr = columns.get(8).text().replaceAll("[$,]", "").trim();

                    marketCapStr = firstNumber(marketCapStr);
                    supplyStr = firstNumber(supplyStr);

                    if (priceStr.isEmpty() || marketCapStr.isEmpty() || supplyStr.isEmpty()) {
                        System.err.println("Строка " + (i + 1) + " (" + nameInfo + "): пустые данные цены/капитализации/supply");
                        errorCount++;
                        continue;
                    }

                    BigDecimal priceUSD = fixDataSuffix(priceStr);
                    BigDecimal marketCapUSD = fixDataSuffix(marketCapStr);
                    BigDecimal circulatingSupply = fixDataSuffix(supplyStr);

                    Cryptocurrency crypto = new Cryptocurrency(
                            cryptoId,
                            nameInfo,
                            symbol.isEmpty() ? nameInfo.substring(0, Math.min(3, nameInfo.length())).toUpperCase() : symbol,
                            nameInfo.toLowerCase().replaceAll(" ", "-"),
                            circulatingSupply,
                            priceUSD,
                            marketCapUSD
                    );

                    cryptoStorage.save(crypto);
                    successCount++;

                } catch (NumberFormatException e) {
                    System.err.println("Строка " + (i + 1) + ": ошибка преобразования числа - " + e.getMessage());
                    errorCount++;
                } catch (IllegalArgumentException e) {
                    System.err.println("Строка " + (i + 1) + ": ошибка валидации - " + e.getMessage());
                    errorCount++;
                } catch (Exception e) {
                    System.err.println("Строка " + (i + 1) + ": неожиданная ошибка - " + e.getMessage());
                    errorCount++;
                }
            }

            System.out.println("Загрузка завершена!");
            System.out.println("Успешно загружено: " + successCount + " криптовалют");
            if (errorCount > 0) {
                System.out.println("Ошибок при загрузке: " + errorCount);
            }

        } catch (Exception e) {
            System.err.println("Критическая ошибка парсинга: " + e.getMessage());
            System.err.println("Проверьте подключение к интернету и доступность сайта");
            e.printStackTrace();
        }
    }
    private BigDecimal fixDataSuffix(String value) {
        value = value.trim().toUpperCase();

        if (value.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal multiplier = BigDecimal.ONE;

        char lastChar = value.charAt(value.length() - 1);
        if (lastChar == 'K') {
            multiplier = new BigDecimal("1000");
            value = value.substring(0, value.length() - 1);
        } else if (lastChar == 'M') {
            multiplier = new BigDecimal("1000000");
            value = value.substring(0, value.length() - 1);
        } else if (lastChar == 'B') {
            multiplier = new BigDecimal("1000000000");
            value = value.substring(0, value.length() - 1);
        } else if (lastChar == 'T') {
            multiplier = new BigDecimal("1000000000000");
            value = value.substring(0, value.length() - 1);
        }

        BigDecimal number = new BigDecimal(value.trim());
        return number.multiply(multiplier);
    }
    private String firstNumber(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        Pattern pattern = Pattern.compile("^([0-9.]+[KMBT]?)");
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return value;
    }
}