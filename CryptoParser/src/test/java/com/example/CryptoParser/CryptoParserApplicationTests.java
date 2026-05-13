package com.example.CryptoParser;
import com.example.CryptoParser.CryptoRepo.CryptoStorage;
import com.example.CryptoParser.CryptoRepo.Cryptocurrency;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
class CryptoParserApplicationTests {

	@Nested
	@DisplayName("Тесты класса Cryptocurrency")
	class TestCryptoCurrency{
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
			Cryptocurrency Etherium = new Cryptocurrency(1, "Etherium", "E", "ETH", 1123122312, new BigDecimal(100000000), new BigDecimal(1001231));
			assertEquals(bitcoin.equals(bitcoin), true);
			assertEquals(bitcoin.equals(Etherium), false);
		}
		@Test
		@DisplayName("Тест функции HashCode")
		void HashCode(){
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(1, "Etherium", "E", "ETH", 113123213, new BigDecimal(1000330), new BigDecimal(10011131));
			assertEquals(bitcoin.hashCode() == Etherium.hashCode() ? true : false, false);
		}
	}

	@Nested
	@DisplayName("Тесты класса CryptoStorage")
	class TestCryptoStorage{
		@Test
		@DisplayName("Тест функции FindByName")
		void FindByName(){
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(1, "Etherium", "E", "ETH", 113123213, new BigDecimal(1000330), new BigDecimal(10011131));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			storage.saveData(Etherium);
			assertEquals(storage.findByName("Bitcoin"), "\n1\nBitcoin\nB\nBTC\n100000000\n1001231\n12030123\n");
			assertEquals(storage.findByName("TON"), "Такой криптовалюты нет");
			IllegalArgumentException exception = assertThrows(
					IllegalArgumentException.class,
					() -> storage.findByName("")
			);
			assertEquals("*/name/* пустое", exception.getMessage());
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
