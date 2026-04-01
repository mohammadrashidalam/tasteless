package com.alam.users.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private HotelDto hotel;
}
