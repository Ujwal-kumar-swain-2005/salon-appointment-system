package com.example.salon_service.repository;

import com.example.salon_service.modal.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {
    List<Salon> findByOwnerId(Long ownerId);

    @Query("SELECT s FROM Salon s WHERE" +
            " LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Salon> searchSalonsByCity(@Param("keyword") String keyword);
}