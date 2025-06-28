package com.example.backend.service;

import com.example.backend.model.Category;
import com.example.backend.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<Category> getAllCategories() {
        return categoriesRepository.findAll().stream().toList();
    }

    public void createCategory(Category category) {
        categoriesRepository.save(category);
    }
}
