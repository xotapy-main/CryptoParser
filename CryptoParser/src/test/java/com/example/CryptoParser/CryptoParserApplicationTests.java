package com.example.CryptoParser;
import com.example.CryptoParser.CryptoRepo.CryptoStorage;
import com.example.CryptoParser.CryptoRepo.Cryptocurrency;
import com.example.CryptoParser.CryptoService.TestClass;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;

import java.util.ArrayList;

@SpringBootTest
class CryptoParserApplicationTests {

	@Nested
	@DisplayName("Тесты класса Cryptocurrency")
	class TestCryptoCurrency{
		@Test
		@DisplayName("Тестирование данных класса")
		public void TestData(){
			long id = 1;
			String name = "Bitcoin";
			String symbol = "B";
			String slug = "BTC";
			BigDecimal circulatinggSupply = new BigDecimal(12030123);
			BigDecimal priceUSD = new BigDecimal(100);
			BigDecimal marketCapUSD = new BigDecimal(10000000);
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100), new BigDecimal(10000000));
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
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			assertEquals(bitcoin.toString(), "\nID: 1\nName: Bitcoin\nSymbol: B\nSlug: BTC\nPrice (USD): 100000000\nMarke Cap. (USD): 1001231\nCirculating supply: 12030123\n");
		}

		@Test
		@DisplayName("Тест функции equals")
		void Equals(){
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(2L, "Etherium", "E", "ETH", new BigDecimal(1123122312), new BigDecimal(100000000), new BigDecimal(1001231));
			assertEquals(bitcoin.equals(bitcoin), true);
			assertEquals(bitcoin.equals(Etherium), false);
		}
		@Test
		@DisplayName("Тест функции HashCode")
		void HashCode(){
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(2L, "Etherium", "E", "ETH", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			assertEquals(bitcoin.hashCode() == Etherium.hashCode() ? true : false, false);
		}
	}

	@Nested
	@DisplayName("Тесты класса CryptoStorage")
	class TestCryptoStorage{
		@Test
		@DisplayName("Тест функции SaveData и GetByName")
		void SaveDataGetByName(){
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			assertEquals(storage.getByName("Bitcoin").equals(bitcoin),true);
		}
		@Test
		@DisplayName("Тест функции FindByName")
		void FindByName(){
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(1L, "Etherium", "E", "ETH", new BigDecimal(113123213), new BigDecimal(1000330), new BigDecimal(10011131));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			storage.saveData(Etherium);
			assertEquals(storage.findByName("Bitcoin"), "\nРезультат поиска:\nID: 1\nName: Bitcoin\nSymbol: B\nSlug: BTC\nPrice (USD): 100000000\nMarke Cap. (USD): 1001231\nCirculating supply: 12030123\n");
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
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(1L, "Etherium", "E", "ETH", new BigDecimal(113123213), new BigDecimal(1000330), new BigDecimal(10011131));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			storage.saveData(Etherium);
			assertFalse(storage.isEmpty());
			storage.clear();
			assertTrue(storage.isEmpty());
		}
		@Test
		@DisplayName("Тест функции GetAll")
		public void GetAll(){
			Cryptocurrency bitcoin = new Cryptocurrency(1L, "Bitcoin", "B", "BTC", new BigDecimal(12030123), new BigDecimal(100000000), new BigDecimal(1001231));
			Cryptocurrency Etherium = new Cryptocurrency(2L, "Etherium", "E", "ETH", new BigDecimal(113123213), new BigDecimal(1000330), new BigDecimal(10011131));
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			storage.saveData(Etherium);
			ArrayList<Cryptocurrency> CryptoList = storage.getAllCrypto();
			assertEquals(CryptoList.size(), 2);
			assertTrue(CryptoList.contains(bitcoin));
			assertTrue(CryptoList.contains(Etherium));
		}
	}
	@Nested
	@DisplayName("Тесты функций класса CryptoService")
	class testCryptoService{
		@Test
		@DisplayName("Тест null")
		public void fixDataWithSufficsNULL(){
			BigDecimal result = (BigDecimal) TestClass.fixDataSuffix("");
			assertEquals(BigDecimal.ZERO, result);
		}
		@Test
		@DisplayName("Тест извлечения число без суффикса")
		public void fixDataWithoutSuffics(){
			BigDecimal result = (BigDecimal) TestClass.fixDataSuffix("22.5");
			assertEquals(new BigDecimal(22.5), result);
		}
		@Test
		@DisplayName("Тест извлечения числа с суфиксом K")
		public void fixDataWithSufficsK(){
			BigDecimal result = (BigDecimal) TestClass.fixDataSuffix("2.25K");
			BigDecimal expected = new BigDecimal("2250");
			assertTrue(result.compareTo(expected) == 0);
		}
		@Test
		@DisplayName("Тест извлечения числа с суфиксом M")
		public void fixDataWithSufficsM(){
			BigDecimal result = (BigDecimal) TestClass.fixDataSuffix("2.25M");
			BigDecimal expected = new BigDecimal("2250000");
			assertTrue(result.compareTo(expected) == 0);
		}
		@Test
		@DisplayName("Тест извлечения числа с суфиксом B")
		public void fixDataWithSufficsB(){
			BigDecimal result = (BigDecimal) TestClass.fixDataSuffix("2.25B");
			BigDecimal expected = new BigDecimal("2250000000");
			assertTrue(result.compareTo(expected) == 0);
		}
		@Test
		@DisplayName("Тест извлечения числа с суфиксом T")
		public void fixDataWithSufficsT(){
			BigDecimal result = (BigDecimal) TestClass.fixDataSuffix("2.25T");
			BigDecimal expected = new BigDecimal("2250000000000");
			assertTrue(result.compareTo(expected) == 0);
		}
		@Test
		@DisplayName("Тест Null")
		public void FirstNumberNULL(){
			String result = TestClass.firstNumber("");
			String result1 = TestClass.firstNumber(null);
			assertEquals("",result);
			assertEquals("",result1);
		}
		@Test
		@DisplayName("Тест извлечение числа без суффикса")
		public void FirstNumberWhitoutSuffics(){
			String result = TestClass.firstNumber("1234kasdbc");
			assertEquals("1234",result);
		}
		@Test
		@DisplayName("Тест извлечение числа с суффиксом K")
		public void FirstNumberWhitSufficsK(){
			String result = TestClass.firstNumber("1234.4Kadsd");
			assertEquals("1234.4K",result);
		}
		@Test
		@DisplayName("Тест извлечение числа с суффиксом M")
		public void FirstNumberWhitSufficsM(){
			String result = TestClass.firstNumber("1234.4Madsd");
			assertEquals("1234.4M",result);
		}
		@Test
		@DisplayName("Тест извлечение числа с суффиксом B")
		public void FirstNumberWhitSufficsB(){
			String result = TestClass.firstNumber("1234.4Badsd");
			assertEquals("1234.4B",result);
		}
		@Test
		@DisplayName("Тест извлечение числа с суффиксом T")
		public void FirstNumberWhitSufficsT(){
			String result = TestClass.firstNumber("1234.4Tadsd");
			assertEquals("1234.4T",result);
		}
	}

}