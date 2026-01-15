package com.example.category_service.repository;

import com.example.category_service.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findBySalonId(Long salonId);
    Category findByIdAndSalonId(Long id, Long salonId);
}