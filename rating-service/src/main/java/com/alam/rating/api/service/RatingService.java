package com.alam.rating.api.service;

import com.alam.rating.api.dto.RatingDto;

import java.util.List;

public interface RatingService {
    RatingDto saveRating(RatingDto ratingDto);
    RatingDto getRatingById(String id);
    List<RatingDto> getAllRatings();
    List<RatingDto> getRatingByUserId(String userId);
    List<RatingDto> getRatingByHotelId(String hotelId);
}
