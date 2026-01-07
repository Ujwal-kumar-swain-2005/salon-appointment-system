package com.example.category_service.controller;

import com.example.category_service.modal.Category;
import com.example.category_service.payload.SalonDto;
import com.example.category_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);
        Category savedCategory = categoryService.saveCategory(category, salonDto);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId) throws Exception {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        categoryService.deleteCategoryById(categoryId, salonDto.getId());
        return ResponseEntity.ok("Category deleted successfully");
    }
}