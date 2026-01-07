package com.example.offering_service.repository;

import com.example.offering_service.modal.Offering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OfferingRepository extends JpaRepository<Offering, Long> {
    Set<Offering> findBySalonId(Long salonId);
}