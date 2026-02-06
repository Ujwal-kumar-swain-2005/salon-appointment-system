package com.example.category_service.controller;

import com.example.category_service.client.SalonFeignClient;
import com.example.category_service.modal.Category;
import com.example.category_service.payload.SalonDto;
import com.example.category_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SalonFeignClient salonFeignClient;
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String token) throws Exception {
        var salonList = salonFeignClient.getSalonsByOwnerId(token).getBody();

        if (salonList == null || salonList.isEmpty()) {
            throw new Exception("No salon found for this owner!");
        }
        var salonDto = salonList.stream()
                .filter(s -> s.getId().equals(category.getSalonId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Salon ID does not belong to this owner!"));
        Category savedCategory = categoryService.saveCategory(category, salonDto);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId, @RequestHeader("Authorization") String token) throws Exception {
        List<SalonDto> salonList = salonFeignClient.getSalonsByOwnerId(token).getBody();

        if (salonList == null || salonList.isEmpty()) {
            throw new Exception("No salon found for this owner!");
        }
        Category category = categoryService.getCategoryById(categoryId);

        if (category == null) {
            throw new Exception("Category not found!");
        }
        Long salonIdFromCategory = category.getSalonId();
        boolean belongsToOwner = salonList.stream()
                .anyMatch(s -> s.getId().equals(salonIdFromCategory));

        if (!belongsToOwner) {
            throw new Exception("You do not have permission to delete this category!");
        }
        categoryService.deleteCategoryById(categoryId, salonIdFromCategory);

        return ResponseEntity.ok("Category deleted successfully");

    }
    @GetMapping("/salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCategoriesByIdAndSalon(@PathVariable Long salonId  , @PathVariable Long id ) throws Exception{
        Category catergory=categoryService.findByIdAndSalonId(id,salonId);
        return ResponseEntity.ok(catergory);

    }
}