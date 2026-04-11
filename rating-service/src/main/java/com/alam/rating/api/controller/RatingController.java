package com.alam.rating.api.controller;

import com.alam.rating.api.dto.RatingDto;
import com.alam.rating.api.repository.RatingRepository;
import com.alam.rating.api.service.RatingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {
    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/saveRating")
    public ResponseEntity<RatingDto> saveRating(@RequestBody RatingDto ratingDto) {
        String uuid= UUID.randomUUID().toString();
        ratingDto.setRatingId(uuid);
        RatingDto ratingDto1 = ratingService.saveRating(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ratingDto1);
    }

    @GetMapping("/getAllRatings")
    public ResponseEntity<?> getAllRatings() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getAllRatings());
    }

    @GetMapping("/getRatingById/{ratingId}")
    public ResponseEntity<?> getRatingById(@PathVariable("ratingId") String ratingId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getRatingById(ratingId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getRatingByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getRatingByUserId(userId));
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<?> getRatingByHotelId(@PathVariable("hotelId") String hotelId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getRatingByHotelId(hotelId));
    }
}
