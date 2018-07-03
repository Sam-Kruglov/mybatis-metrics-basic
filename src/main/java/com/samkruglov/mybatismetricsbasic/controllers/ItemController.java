package com.samkruglov.mybatismetricsbasic.controllers;

import com.samkruglov.mybatismetricsbasic.domain.Item;
import com.samkruglov.mybatismetricsbasic.services.ItemService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
public class ItemController {
    
    ItemService service;
    
    @GetMapping("{id}")
    public ResponseEntity<Item> getOne(@PathVariable("id") String id) {
        
        return ResponseEntity.ok(service.getOne(id));
    }
    
    @PostMapping
    public ResponseEntity save(@RequestBody Item item) {
        
        service.save(item);
        
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        
        service.delete(id);
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<Set<Item>> getAll() {
        
        return ResponseEntity.ok(service.getAll());
    }
}
