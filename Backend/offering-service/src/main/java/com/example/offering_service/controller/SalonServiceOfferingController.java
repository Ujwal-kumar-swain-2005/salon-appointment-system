package com.example.offering_service.controller;

import com.example.offering_service.modal.Offering;
import com.example.offering_service.payload.CategoryDto;
import com.example.offering_service.payload.OfferingDto;
import com.example.offering_service.payload.SalonDto;
import com.example.offering_service.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    @Autowired
    private OfferingService offeringService;

    @PostMapping
    public ResponseEntity<Offering> createServiceOffering(@RequestBody OfferingDto offeringDto) {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(offeringDto.getCategoryId());

        var offering = offeringService.createServiceOffering(salonDto, offeringDto, categoryDto);
        return ResponseEntity.ok(offering);

    }

    @PostMapping("/{id}")
    public ResponseEntity<Offering> updateServiceOffering(@PathVariable Long id, @RequestBody Offering offering) throws Exception {
        return ResponseEntity.ok(offeringService.updateServiceOffering(id, offering));
    }
}