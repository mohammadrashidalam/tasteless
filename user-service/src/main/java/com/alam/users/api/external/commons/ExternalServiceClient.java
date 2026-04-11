package com.alam.users.api.external.commons;

import com.alam.users.api.dto.HotelDto;
import com.alam.users.api.dto.RatingDto;

import java.util.List;

public interface ExternalServiceClient {
    List<RatingDto> getRatingsByUserId(String userId);

    HotelDto getHotelById(String hotelId);

    RatingDto saveRating(RatingDto ratingDto);

    HotelDto saveHotelDetails(HotelDto hotelDto);
}
