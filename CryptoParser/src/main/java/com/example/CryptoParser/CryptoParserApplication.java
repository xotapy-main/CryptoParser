package com.example.CryptoParser;
import com.example.CryptoParser.CryptoRepo.CryptoStorage;
import com.example.CryptoParser.CryptoRepo.Cryptocurrency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootApplication
public class CryptoParserApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(CryptoParserApplication.class, args);
			Cryptocurrency bitcoin = new Cryptocurrency(1, "Bitcoin", "B", "BTC", 12030123, new BigDecimal(100000000), new BigDecimal(1001231));
			System.out.println("########## BITCOIN ###########"+ bitcoin.toString());
			Cryptocurrency etherium = new Cryptocurrency(2, "Etherium", "E", "ETH", 123132, new BigDecimal(2400), new BigDecimal(112311));
			System.out.println("########## ETHERIUM ###########"+ etherium.toString());
			System.out.println("eheterium = bitcoin? : " + bitcoin.equals(etherium) + "\n\n");
			CryptoStorage storage = new CryptoStorage();
			storage.saveData(bitcoin);
			storage.saveData(etherium);
			System.out.println(storage.findByName("Etherium"));
		}
		catch (Error message){
			System.out.println("Ошибка валидации: " + message);
		}
	}

}
