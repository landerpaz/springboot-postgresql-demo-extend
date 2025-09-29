package com.example.demo.repo;

import com.example.demo.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ItemRepositoryCustom {
    Page<Item> searchByFilters(Map<String, Object> filters, Pageable pageable);
}
