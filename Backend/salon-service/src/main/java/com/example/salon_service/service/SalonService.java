package com.example.salon_service.service;

import com.example.salon_service.modal.Salon;
import com.example.salon_service.payload.SalonDto;
import com.example.salon_service.payload.UserDto;

import java.util.List;

public interface SalonService {
    Salon createSalon(SalonDto salonDto, UserDto userDto);
    Salon updateSalon(SalonDto salonDto, UserDto userDto, Long salonId) throws Exception;
    List<Salon> getAllSalons();
    Salon getSalonById(Long salonId) throws Exception;
    List<Salon> getSalonsByOwnerId(Long ownerId);
    List<Salon> searchSalonsByCity(String city);
}