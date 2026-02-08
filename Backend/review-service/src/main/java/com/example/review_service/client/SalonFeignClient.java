package com.example.review_service.client;

import com.example.review_service.payload.SalonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("SALON-SERVICE")
public interface SalonFeignClient {

    @GetMapping("/api/salons/owner")
    public ResponseEntity<List<SalonDto>> getSalonsByOwnerId(@RequestHeader("Authorization") String token) throws Exception;

    @GetMapping("/api/salons/{salonId}")
    public ResponseEntity<SalonDto> getSalonById(@PathVariable Long salonId) throws Exception;
}