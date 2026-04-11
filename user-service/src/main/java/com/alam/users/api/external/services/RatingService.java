package com.alam.users.api.external.services;

import com.alam.users.api.constant.ApiPaths;
import com.alam.users.api.dto.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
    @PostMapping(ApiPaths.SAVE_RATING)
    RatingDto saveRating(@RequestBody RatingDto ratingDto);
    @GetMapping(ApiPaths.GET_RATING_BY_USER_ID+"/{userId}")
    List<RatingDto> getRatingsByUserId(@PathVariable("userId") String userId);
}
