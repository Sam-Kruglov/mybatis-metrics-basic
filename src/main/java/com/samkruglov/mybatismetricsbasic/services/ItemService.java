package com.samkruglov.mybatismetricsbasic.services;

import com.samkruglov.mybatismetricsbasic.domain.Item;
import com.samkruglov.mybatismetricsbasic.repositories.ItemRepo;
import com.samkruglov.mybatismetricsbasic.utils.exceptions.StatusException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ItemService {
    
    ItemRepo repo;
    
    public Item getOne(String id) {
        
        return Optional.ofNullable(repo.findOne(id))
                       .orElseThrow(() -> new StatusException("Item #" + id + " was not found.", HttpStatus.NOT_FOUND));
    }
    
    public void insert(Item item) {
        
        repo.insert(item);
    }
    
    public void update(String id, Item item) {
        
        repo.update(id, item);
    }
    
    public void delete(String item) {
        
        repo.delete(item);
    }
    
    public Set<Item> getAll() {
        
        return repo.findAll();
    }
}
