package com.example.offering_service.controller;

import com.example.offering_service.modal.Offering;
import com.example.offering_service.payload.CategoryDto;
import com.example.offering_service.payload.OfferingDto;
import com.example.offering_service.payload.SalonDto;
import com.example.offering_service.service.OfferingService;
import com.example.offering_service.service.client.CategoryFeignClient;
import com.example.offering_service.service.client.SalonFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    @Autowired
    private OfferingService offeringService;
    @Autowired
    private SalonFeignClient salonFeignClient;
    @Autowired
    private CategoryFeignClient categoryFeignClient;
    @PostMapping
    public ResponseEntity<Offering> createServiceOffering(@RequestBody OfferingDto offeringDto, @RequestHeader("Authorization") String token) throws Exception {
        List<SalonDto> salonList = salonFeignClient.getSalonsByOwnerId(token).getBody();
        if (salonList == null || salonList.isEmpty()) {
            throw new Exception("No salon found for this owner!");
        }
        SalonDto selectedSalon = salonList.stream()
                .filter(s -> s.getId().equals(offeringDto.getSalonId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Salon ID not found for this owner"));
        ResponseEntity<CategoryDto> categoryResponse =
                categoryFeignClient.getCategoriesByIdAndSalon(
                        offeringDto.getSalonId(),
                        offeringDto.getCategoryId(),
                        token
                );

        CategoryDto categoryDto = categoryResponse.getBody();
        if (categoryDto == null) {
            throw new Exception("Category not found for this salon!");
        }
        Offering offering = offeringService.createServiceOffering(
                selectedSalon,
                offeringDto,
                categoryDto
        );

        return ResponseEntity.ok(offering);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Offering> updateServiceOffering(@PathVariable Long id, @RequestBody Offering offering) throws Exception {
        return ResponseEntity.ok(offeringService.updateServiceOffering(id, offering));
    }
}