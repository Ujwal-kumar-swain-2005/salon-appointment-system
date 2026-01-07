package com.example.offering_service.controller;

import com.example.offering_service.modal.Offering;
import com.example.offering_service.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
public class OfferingController {

    @Autowired
    private OfferingService offeringService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<Offering>> getServiceOfferingBySalonId(@PathVariable Long salonId,
                                                                     @RequestParam(required = false) Long categoryId) {
        var offering = offeringService.getAllOfferingBySalonId(salonId, categoryId);
        return ResponseEntity.ok(offering);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Offering> getServiceOfferingById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(offeringService.getOfferingById(id));

    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<Offering>> getServiceOfferingByIds(@PathVariable Set<Long> ids) {
        return ResponseEntity.ok(offeringService.getOfferingByIds(ids));
    }
}