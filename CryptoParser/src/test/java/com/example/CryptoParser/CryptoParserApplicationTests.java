package com.example.CryptoParser;
import com.example.CryptoParser.CryptoRepo.CryptoStorage;
import com.example.CryptoParser.CryptoRepo.Cryptocurrency;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class CryptoParserApplicationTests {

	@Nested
	@DisplayName("Тесты класса Cryptocurrency")
	class TestCryptoCurrency{
		@Test
		@DisplayName("Тестирование данных класса")
		public void TestData(){
			int id = 1;
			String name = "Bitcoin";
			String symbol = "B";
			String slug = "BTC";
			long circulatinggSupply = 12030123;
			BigDecimal priceUSD = new BigDecimal(100);
			BigDecimal marketCapUSD = new BigDecimal(10000000);
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100), new BigDecimal(10000000));
			assertEquals(bitcoin.getId(), id);
			assertEquals(bitcoin.getName(), name);
			assertEquals(bitcoin.getSymbol(), symbol);
			assertEquals(bitcoin.getSlug(), slug);
			assertEquals(bitcoin.getCirculatingSupply(), circulatinggSupply);
			assertEquals(bitcoin.getPriceUSD(), priceUSD);
			assertEquals(bitcoin.getMarketCapUSD(), marketCapUSD);
		}
		@Test
		@DisplayName("Тест функции toString")
		void ToString() {
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			assertEquals(bitcoin.toString(), "\n1\nBitcoin\nB\nBTC\n100000000\n1001231\n12030123\n");
		}

		@Test
		@DisplayName("Тест функции equals")
		void Equals(){
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(2, "Etherium", "E", "ETH", 1123122312, new BigDecimal(100000000), new BigDecimal(1001231));
			assertEquals(bitcoin.equals(bitcoin), true);
			assertEquals(bitcoin.equals(Etherium), false);
		}
		@Test
		@DisplayName("Тест функции HashCode")
		void HashCode(){
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(2, "Etherium", "E", "ETH", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			assertEquals(bitcoin.hashCode() == Etherium.hashCode() ? true : false, false);
		}
	}

	@Nested
	@DisplayName("Тесты класса CryptoStorage")
	class TestCryptoStorage{
		@Test
		@DisplayName("Тест функции SaveData и GetByName")
		void SaveDataGetByName(){
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			assertEquals(storage.getByName("Bitcoin").equals(bitcoin),true);
		}
		@Test
		@DisplayName("Тест функции FindByName")
		void FindByName(){
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(1, "Etherium", "E", "ETH", 113123213, new BigDecimal(1000330), new BigDecimal(10011131));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			storage.saveData(Etherium);
			assertEquals(storage.findByName("Bitcoin"), "\nРезультат поиска:\n1\nBitcoin\nB\nBTC\n100000000\n1001231\n12030123\n");
			assertEquals(storage.findByName("TON"), "\nРезультат поиска:\nТакой криптовалюты нет");
			IllegalArgumentException exception = assertThrows(
					IllegalArgumentException.class,
					() -> storage.findByName("")
			);
			assertEquals("\n*/name/* пустая строка\n", exception.getMessage());
			IllegalArgumentException ex = assertThrows(
					IllegalArgumentException.class,
					() -> storage.findByName(null)
			);
			assertEquals(ex.getMessage(), "\n*/name/* не может быть null\n");
		}
		@Test
		@DisplayName("Тест функции Clear")
		void Clear(){
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(1, "Etherium", "E", "ETH", 113123213, new BigDecimal(1000330), new BigDecimal(10011131));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			storage.saveData(Etherium);
			assertEquals(storage.isEmpty(), false);
			storage.clear();
			assertEquals(storage.isEmpty(), true);
		}
	}

}
