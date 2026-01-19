package com.example.salon_service.controller;

import com.example.salon_service.payload.SalonDto;
import com.example.salon_service.payload.SalonMapper;
import com.example.salon_service.payload.UserDto;
import com.example.salon_service.service.SalonService;
import com.example.salon_service.service.client.UserFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
@RestController
@RequestMapping("/api/salons")

public class SalonController {
    private static final Logger log =
            LoggerFactory.getLogger(SalonController.class);
    private final SalonService salonService;
    private final UserFeignClient userFeignClient;
    public SalonController(SalonService salonService,UserFeignClient userFeignClient) {
        this.salonService = salonService;
        this.userFeignClient = userFeignClient;
    }

    @PostMapping("/create")
    public ResponseEntity<SalonDto> createSalon(@RequestBody SalonDto salonDto,@RequestHeader("Authorization") String token) throws Exception {
        UserDto userDto = userFeignClient.getUserInfo(token).getBody();
        log.info("Calling USER-SERVICE with token");
        if(userDto == null) {
            throw new Exception("User not found from token");
        }
        System.out.println(userDto);
        var salon = salonService.createSalon(salonDto, userDto);
        SalonDto createdSalonDto = SalonMapper.toDto(salon);
        return ResponseEntity.ok(createdSalonDto);

    }

    @PutMapping("/{salonId}")
    public ResponseEntity<SalonDto> updateSalon(@RequestBody SalonDto salonDto, @PathVariable Long salonId,@RequestHeader("Authorization") String token)
            throws Exception {
        UserDto userDto = userFeignClient.getUserInfo(token).getBody();

        var salon = salonService.updateSalon(salonDto, userDto, salonId);
        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDto> getSalonById(@PathVariable Long salonId) throws Exception {
        var salon = salonService.getSalonById(salonId);
        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

    @GetMapping
    public ResponseEntity<List<SalonDto>> getAllSalons() {
        var salons = salonService.getAllSalons();
        var salonDtos = salons.stream().map(SalonMapper::toDto).toList();
        return ResponseEntity.ok(salonDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDto>> searchSalonsByCity(@RequestParam String city) {
        var salons = salonService.searchSalonsByCity(city);
        var salonDtos = salons.stream().map(SalonMapper::toDto).toList();
        return ResponseEntity.ok(salonDtos);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<SalonDto>> getSalonsByOwnerId(
            @RequestHeader("Authorization") String token) throws Exception {

        UserDto userDto = userFeignClient.getUserInfo(token).getBody();

        if (userDto == null || userDto.getId() == null) {
            throw new RuntimeException("User not resolved from token");
        }
        log.info("UserDto received: id={}, fullName={}, email={}",
                userDto.getId(), userDto.getFullName(), userDto.getEmail());

        var salons = salonService.getSalonsByOwnerId(userDto.getId());

        List<SalonDto> salonDtos = salons.stream()
                .map(SalonMapper::toDto)
                .toList();

        return ResponseEntity.ok(salonDtos);
    }

}