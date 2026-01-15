package com.example.category_service.client;

import com.example.category_service.payload.SalonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("SALON-SERVICE")
public interface SalonFeignClient {

    @GetMapping("/api/salons/owner")
    public ResponseEntity<List<SalonDto>> getSalonsByOwnerId(@RequestHeader("Authorization") String token) throws Exception;
}