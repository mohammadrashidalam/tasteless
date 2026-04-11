package com.alam.hotels.api.controller;

import com.alam.hotels.api.dto.HotelDto;
import com.alam.hotels.api.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    private HotelService hotelService;
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    @PostMapping("/saveHotelDetails")
    public ResponseEntity<HotelDto> saveHotelDetails(@RequestBody HotelDto hotelDto) {
        String uuid= UUID.randomUUID().toString();
        hotelDto.setId(uuid);
        HotelDto hotelDto1 = hotelService.saveHotelDetails(hotelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelDto1);
    }
    @GetMapping("/getAllHotels")
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<HotelDto> hotelDtos = hotelService.findAllHotels();
        return ResponseEntity.status(HttpStatus.OK).body(hotelDtos);
    }
    @GetMapping("/getHotelById/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id") String id) {
        HotelDto hotelDto = hotelService.findHotelById(id);
        return ResponseEntity.status(HttpStatus.OK).body(hotelDto);
    }
}
