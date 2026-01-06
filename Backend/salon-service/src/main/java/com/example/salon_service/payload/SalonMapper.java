package com.example.salon_service.payload;

import com.example.salon_service.modal.Salon;

public class SalonMapper {
    public static SalonDto toDto(Salon salon) {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(salon.getId());
        salonDto.setName(salon.getName());
        salonDto.setEmail(salon.getEmail());
        salonDto.setImages(salon.getImages());
        salonDto.setAddress(salon.getAddress());
        salonDto.setPhoneNumber(salon.getPhoneNumber());
        salonDto.setCity(salon.getCity());
        salonDto.setOpeningTime(salon.getOpeningTime());
        salonDto.setClosingTime(salon.getClosingTime());
        salonDto.setOwnerId(salon.getOwnerId());

        return salonDto;
    }
}