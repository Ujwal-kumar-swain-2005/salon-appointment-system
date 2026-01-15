package com.example.category_service.service;

import com.example.category_service.modal.Category;
import com.example.category_service.payload.SalonDto;

import java.util.Set;

public interface CategoryService {
    Category saveCategory(Category category, SalonDto salonDto);
    Set<Category> getAllCategoriesBySalonId(Long salonId);
    Category getCategoryById(Long categoryId) throws Exception;
    void deleteCategoryById(Long categoryId, Long salonId) throws Exception;
    Category findByIdAndSalonId(Long id, Long salonId) throws Exception;
}