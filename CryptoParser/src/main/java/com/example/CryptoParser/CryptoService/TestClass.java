package com.example.CryptoParser.CryptoService;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    public static BigDecimal fixDataSuffix(String value) {
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

    public static String firstNumber(String value) {
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