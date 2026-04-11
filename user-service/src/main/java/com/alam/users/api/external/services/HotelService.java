package com.alam.users.api.external.services;

import com.alam.users.api.constant.ApiPaths;
import com.alam.users.api.dto.HotelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Component
@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {
    @GetMapping(ApiPaths.HOTEL_GET_BY_ID+"/{id}")
    HotelDto getHotelById(@PathVariable String id);

    @PostMapping(ApiPaths.SAVE_HOTEL)
    HotelDto saveHotelDetails(@RequestBody HotelDto hotelDto);
}
