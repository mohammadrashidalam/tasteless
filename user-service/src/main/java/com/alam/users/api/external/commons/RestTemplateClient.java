package com.alam.users.api.external.commons;

import com.alam.users.api.constant.ApiPaths;
import com.alam.users.api.dto.HotelDto;
import com.alam.users.api.dto.RatingDto;
import com.alam.users.api.logger.BaseLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service("restTemplateCall")
@RequiredArgsConstructor
public class RestTemplateClient extends BaseLogger implements ExternalServiceClient {
    private final RestTemplate restTemplate;
    @Override
    public List<RatingDto> getRatingsByUserId(String userId) {
        logger.info("Fetching ratings for userId: {}", userId);
        try {
            RatingDto[] ratingsArray = restTemplate.getForObject(ApiPaths.RATING_ENDPOINT, RatingDto[].class);
            assert ratingsArray != null;
            return Arrays.asList(ratingsArray);
        } catch (Exception ex) {
            logger.error("Error fetching ratings for userId: {}{}", userId, ex.getMessage());
            throw new RuntimeException("Failed to fetch ratings", ex);
        }
    }
    @Override
    public HotelDto getHotelById(String hotelId) {
        logger.info("Fetching hotel details for hotelId: {}", hotelId);
        try {
            return restTemplate.getForObject(ApiPaths.HOTEL_ENDPOINT + "/" + hotelId, HotelDto.class);
        } catch (Exception ex) {
            logger.error("Error fetching hotel details for hotelId: {}{}", hotelId, ex.getMessage());
            throw new RuntimeException("Failed to fetch hotel details", ex);
        }
    }

    @Override
    public RatingDto saveRating(RatingDto ratingDto) {
        logger.info("Saving rating: {}", ratingDto);
         try {
             return restTemplate.postForObject(ApiPaths.SAVE_RATING, ratingDto, RatingDto.class);
         } catch (Exception ex) {
             logger.error("Error saving rating: {}{}", ratingDto, ex.getMessage());
             throw new RuntimeException("Failed to save rating", ex);
         }

    }

    @Override
    public HotelDto saveHotelDetails(HotelDto hotelDto) {
        logger.info("Saving hotel details: {}", hotelDto);
        try {
            return restTemplate.postForObject(ApiPaths.SAVE_HOTEL, hotelDto, HotelDto.class);
        } catch (Exception ex) {
            logger.error("Error saving hotel details: {}{}", hotelDto, ex.getMessage());
            throw new RuntimeException("Failed to save hotel details", ex);
        }
    }
}
