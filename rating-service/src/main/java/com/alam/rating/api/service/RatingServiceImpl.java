package com.alam.rating.api.service;

import com.alam.rating.api.dto.RatingDto;
import com.alam.rating.api.entities.Rating;
import com.alam.rating.api.exception.ResourceNotFoundException;
import com.alam.rating.api.repository.RatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    private ModelMapper modelMapper;

    public RatingServiceImpl(RatingRepository ratingRepository, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * @param ratingDto
     * @return
     */
    @Override
    public RatingDto saveRating(RatingDto ratingDto) {
        Rating  rating = modelMapper.map(ratingDto, Rating.class);
        Rating saveRating = ratingRepository.save(rating);
        return modelMapper.map(saveRating, RatingDto.class);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public RatingDto getRatingById(String id) {
        Rating rating=ratingRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Rating not foung with given id - "+id));
        return modelMapper.map(rating, RatingDto.class);
    }

    /**
     * @return
     */
    @Override
    public List<RatingDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(rating->modelMapper.map(rating, RatingDto.class))
                .toList();
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<RatingDto> getRatingByUserId(String userId) {
        List<Rating> ratingList=ratingRepository.findByUserId(userId);

        return ratingList.stream()
                .map(rating->modelMapper.map(rating, RatingDto.class))
                .toList();
    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<RatingDto> getRatingByHotelId(String hotelId) {
        List<Rating> ratingList=ratingRepository.findByHotelId(hotelId);

        return ratingList.stream()
                .map(rating->modelMapper.map(rating, RatingDto.class))
                .toList();
    }
}
