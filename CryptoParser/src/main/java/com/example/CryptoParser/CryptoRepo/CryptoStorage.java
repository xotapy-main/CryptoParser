package com.example.CryptoParser.CryptoRepo;

import org.springframework.stereotype.Component;

import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
@Component
public class CryptoStorage {
    private Map<String, Cryptocurrency> storage = new HashMap<>();
    private static int counter = 0;
    public void saveData(Cryptocurrency value){
        storage.put(value.getName(), value);
        counter++;
        System.out.println("Дебаг: добалено значение криптовалюты новое" + value.getName() + " номер: " + counter);
    }
    public String findByName(String name){
        validateStorage(storage);
        validateInputName(name);
        Cryptocurrency temp = storage.get(name);
        if(temp == null){
            return "\nРезультат поиска:\nТакой криптовалюты нет";
        }
        return "\nРезультат поиска:"+ temp.toString();
    }
    public void clear(){
        storage.clear();
        System.out.println("Хранилище очищено! ( Status: " + storage.isEmpty() + " )");
    }
    public Cryptocurrency getByName(String name){
        validateInputName(name);
        return storage.get(name);
    }
    public boolean isEmpty(){
        return storage.isEmpty();
    }
    private void validateStorage(Map<String, Cryptocurrency> value){
        if(value.isEmpty()){
            throw new IllegalArgumentException("Хранилище пустое!\n");
        }
    }
    public void validateInputName(String name){
        if(name == null){
            throw new IllegalArgumentException("\n*/name/* не может быть null\n");
        }
        else if(name.isEmpty()){
            throw new IllegalArgumentException("\n*/name/* пустая строка\n");
        }
    }
}