package com.example.CryptoParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class CryptoParserApplication {

	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream(System.out, true, "UTF-8"));
			System.setErr(new PrintStream(System.err, true, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SpringApplication.run(CryptoParserApplication.class, args);
	}
}