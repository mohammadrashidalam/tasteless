package com.alam.hotels.api.service;

import com.alam.hotels.api.dto.HotelDto;
import com.alam.hotels.api.entites.Hotel;

import java.util.List;

public interface HotelService {
    HotelDto saveHotelDetails(HotelDto hotelDto);
    HotelDto findHotelById(String id);
    List<HotelDto> findAllHotels();
}
