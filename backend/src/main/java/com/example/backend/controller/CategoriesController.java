package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            return ResponseEntity.ok(categoriesService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEntity(@RequestBody Category category) {
        try {
            categoriesService.createCategory(category);
            return ResponseEntity.ok("Category created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @GetMapping("/{eventId}")
//    public ResponseEntity<List<Category>> getEventCategories
}
