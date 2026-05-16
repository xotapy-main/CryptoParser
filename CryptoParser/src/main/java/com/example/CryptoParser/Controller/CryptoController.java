package com.example.CryptoParser.Controller;

import com.example.CryptoParser.CryptoRepo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {
    private CryptoStorage storage;
    public CryptoController(CryptoStorage storage){
        this.storage = storage;
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<Cryptocurrency> search(@PathVariable String name){
        Cryptocurrency result = storage.getByName(name);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
