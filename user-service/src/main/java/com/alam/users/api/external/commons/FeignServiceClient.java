package com.alam.users.api.external.commons;

import com.alam.users.api.dto.HotelDto;
import com.alam.users.api.dto.RatingDto;
import com.alam.users.api.external.services.HotelService;
import com.alam.users.api.external.services.RatingService;
import com.alam.users.api.logger.BaseLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("feignCallbackClient")
@RequiredArgsConstructor
public class FeignServiceClient extends BaseLogger implements ExternalServiceClient {
    private final RatingService ratingService;
    private final HotelService hotelService;

    @Override
    public List<RatingDto> getRatingsByUserId(String userId) {
        logger.info("FeignServiceClient: getRatingsByUserId called with userId: {}", userId);
        return ratingService.getRatingsByUserId(userId);
    }

    @Override
    public HotelDto getHotelById(String hotelId) {
        logger.info("FeignServiceClient: getHotelById called with hotelId: {}", hotelId);
        return hotelService.getHotelById(hotelId);
    }

    @Override
    public RatingDto saveRating(RatingDto ratingDto) {
        logger.info("FeignServiceClient: saveRating called with ratingDto: {}", ratingDto);
        return ratingService.saveRating(ratingDto);
    }

    @Override
    public HotelDto saveHotelDetails(HotelDto hotelDto) {
        logger.info("FeignServiceClient: saveHotelDetails called with hotelDto: {}", hotelDto);
        return hotelService.saveHotelDetails(hotelDto);
    }
}
