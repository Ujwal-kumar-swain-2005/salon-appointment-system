package com.example.category_service.service;

import com.example.category_service.modal.Category;
import com.example.category_service.payload.SalonDto;
import com.example.category_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDto salonDto) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDto.getId());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalonId(Long salonId) {
        return categoryRepository.findBySalonId(salonId);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws Exception {
        var category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new Exception("Category not found with id: " + categoryId);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(Long categoryId, Long salonId) throws Exception {
        Category category = getCategoryById(categoryId);
        if (!category.getSalonId().equals(salonId)) {
            throw new Exception("Category with id: " + categoryId + " does not belong to salon with id: " + salonId);
        }

        categoryRepository.deleteById(categoryId);

    }

    @Override
    public Category findByIdAndSalonId(Long id, Long salonId) throws Exception {
        var category = categoryRepository.findByIdAndSalonId(id, salonId);
        if (category == null) {
            throw new Exception("Category not found with ID:" + id);
        }

        return category;
    }

}