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
        if(name.isEmpty()){
            throw new IllegalArgumentException("*/name/* пустое");
        }
        Cryptocurrency temp = storage.get(name);
        if(temp == null){
            return "Такой криптовалюты нет";
        }
        return temp.toString();
    }
    public ArrayList<Cryptocurrency> getAll(){
        validateStorage(storage);
        return new ArrayList<>(storage.values());
    }
    public void clear(){
        storage.clear();
        System.out.println("Хранилище очищено!" + " " + (storage.isEmpty() ? true : false));
    }
    public void replaceStorage(Map<String, Cryptocurrency> newStorage){
        validateStorage(newStorage);
        this.storage = newStorage;
        System.out.println("Хранилище перемещено!");
    }
    private void validateStorage(Map<String, Cryptocurrency> value){
        if(value.isEmpty()){
            throw new IllegalArgumentException("Хранилище пустое!\n");
        }
    }
    public boolean isEmpty(){
        return storage.isEmpty();
    }
}