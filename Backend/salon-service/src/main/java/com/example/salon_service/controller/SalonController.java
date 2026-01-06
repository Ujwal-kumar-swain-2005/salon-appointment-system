package com.example.salon_service.controller;

import com.example.salon_service.payload.SalonDto;
import com.example.salon_service.payload.SalonMapper;
import com.example.salon_service.payload.UserDto;
import com.example.salon_service.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/salons")
public class SalonController {

    private final SalonService salonService;

    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @PostMapping
    public ResponseEntity<SalonDto> createSalon(@RequestBody SalonDto salonDto) {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        var salon = salonService.createSalon(salonDto, userDto);
        SalonDto createdSalonDto = SalonMapper.toDto(salon);
        return ResponseEntity.ok(createdSalonDto);

    }

    @PutMapping("/{salonId}")
    public ResponseEntity<SalonDto> updateSalon(@RequestBody SalonDto salonDto, @PathVariable Long salonId)
            throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

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

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<SalonDto>> getSalonsByOwnerId(@PathVariable Long ownerId) {
        var salons = salonService.getSalonsByOwnerId(ownerId);
        List<SalonDto> salonDtos = salons.stream().map(SalonMapper::toDto).toList();
        return ResponseEntity.ok(salonDtos);
    }

}