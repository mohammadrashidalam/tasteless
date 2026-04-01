package com.alam.rating.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private String ratingId;
    private String userId;
    private String hotelId;
    private double rating;
    private String feedback;
    private HotelDto hotels;
}
