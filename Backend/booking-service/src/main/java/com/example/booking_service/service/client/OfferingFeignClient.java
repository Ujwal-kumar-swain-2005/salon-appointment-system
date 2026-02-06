package com.example.booking_service.service.client;

import com.example.booking_service.payload.OfferingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient("OFFERING-SERVICE")
public interface OfferingFeignClient {

    @GetMapping("/api/service-offering/list/{ids}")
    public ResponseEntity<Set<OfferingDto>> getServiceOfferingByIds(@PathVariable Set<Long> ids);
}