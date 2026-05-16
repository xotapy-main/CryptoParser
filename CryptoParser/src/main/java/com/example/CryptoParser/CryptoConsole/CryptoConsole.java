package com.example.CryptoParser.CryptoConsole;

import com.example.CryptoParser.CryptoRepo.CryptoStorage;
import com.example.CryptoParser.CryptoRepo.Cryptocurrency;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Scanner;

@Component
@Order(2)
@ConditionalOnProperty(name = "console.interface.enabled", havingValue = "true", matchIfMissing = true)
public class CryptoConsole implements CommandLineRunner {

    private final CryptoStorage cryptoStorage;

    public CryptoConsole(CryptoStorage cryptoStorage) {
        this.cryptoStorage = cryptoStorage;
    }

    @Override
    public void run(String... args) throws Exception {
        int attempts = 0;
        while (cryptoStorage.isEmpty() && attempts < 20) {
            Thread.sleep(500);
            attempts++;
        }

        if (cryptoStorage.isEmpty()) {
            System.out.println("Внимание: данные не загружены. Хранилище пустое.");
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\nКонсольный интерфейс CryptoParser");

        while (running) {
            System.out.println("\nДоступные команды:");
            System.out.println("1. Поиск криптовалюты по названию");
            System.out.println("2. Показать все загруженные криптовалюты");
            System.out.println("3. Выход");
            System.out.print("\nВведите номер команды: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    searchCrypto(scanner);
                    break;
                case "2":
                    showAllCrypto();
                    break;
                case "3":
                    System.out.println("\nЗавершение работы программы...");
                    running = false;
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nНеверная команда! Попробуйте снова.");
            }
        }

        scanner.close();
    }

    private void searchCrypto(Scanner scanner) {
        System.out.print("\nВведите название криптовалюты: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Название не может быть пустым!");
            return;
        }

        try {
            String result = cryptoStorage.findByName(name);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllCrypto() {
        try {
            ArrayList<Cryptocurrency> allCrypto = cryptoStorage.getAllCrypto();
            allCrypto.sort((p1, p2) -> Long.compare(p1.getId(), p2.getId()));
            System.out.println("Список всех криптовалют\n");

            for (Cryptocurrency crypto : allCrypto) {
                System.out.println(crypto.toString());
            }
            System.out.println("Всего криптовалют: " + allCrypto.size());

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}