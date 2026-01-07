package com.example.offering_service.service;

import com.example.offering_service.modal.Offering;
import com.example.offering_service.payload.CategoryDto;
import com.example.offering_service.payload.OfferingDto;
import com.example.offering_service.payload.SalonDto;

import java.util.Set;

public interface OfferingService {
    Offering createServiceOffering(SalonDto salonDto, OfferingDto offeringDto, CategoryDto categoryDto);

    Offering updateServiceOffering(Long offeringId, Offering offering) throws Exception;

    Set<Offering> getAllOfferingBySalonId(Long salonId, Long categoryId);

    Set<Offering> getOfferingByIds(Set<Long> ids);

    Offering getOfferingById(Long id) throws Exception;

}