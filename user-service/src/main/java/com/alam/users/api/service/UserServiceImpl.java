package com.alam.users.api.service;

import com.alam.users.api.constant.ApiPaths;
import com.alam.users.api.dto.HotelDto;
import com.alam.users.api.dto.RatingDto;
import com.alam.users.api.dto.UserDto;
import com.alam.users.api.entities.User;
import com.alam.users.api.exception.ResourceNotFoundException;
import com.alam.users.api.external.commons.ExternalServiceClient;
import com.alam.users.api.external.factory.ExternalServicesFactory;
import com.alam.users.api.logger.BaseLogger;
import com.alam.users.api.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseLogger implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private ExternalServicesFactory factory;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper, ExternalServicesFactory factory
    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.factory = factory;
    }

    /**
     * @param userDto:UserDto
     * @return UserDto
     */
    @Override
    public UserDto save(UserDto userDto) {
        String randomUserId = UUID.randomUUID().toString();
        User user = modelMapper.map(userDto, User.class);
        user.setUuid(randomUserId);

        User saveUser = userRepository.save(user);
        UserDto userDto1 = modelMapper.map(saveUser, UserDto.class);
        userDto1.setUserId(user.getUuid());
        return userDto1;
    }

    /**
     * .
     *
     * @return List<UserDto>
     */
    @Override
    public List<UserDto> getAllUser() {
        List<User> listOfUser = userRepository.findAll();
        List<UserDto> dtoList = listOfUser.stream()
                .map(user -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    userDto.setUserId(user.getUuid());
                    ExternalServiceClient client = this.getExternalServiceClient(ApiPaths.FEIGN_CLIENT_CALL);
                    return mapRatingAndHotelData(userDto.getUserId(), client, userDto);
                })
                .toList();
        return dtoList;
    }

    /**
     * @param userId : userId
     * @return UserDto
     */
    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found given userId " + userId));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setUserId(user.getUuid());
        ExternalServiceClient client = this.getExternalServiceClient(ApiPaths.FEIGN_CLIENT_CALL);
        return mapRatingAndHotelData(userId, client, userDto);
    }

    private UserDto mapRatingAndHotelData(String userId, ExternalServiceClient client, UserDto userDto) {
        logger.info("Fetching ratings for userId: {}", userId);
        List<RatingDto> ratingDtos = client.getRatingsByUserId(userId);
        if (!ratingDtos.isEmpty()) {

            List<RatingDto> ratingList = ratingDtos.stream().peek(ratingDto -> {
                try {
                    HotelDto hotelById = client.getHotelById(ratingDto.getHotelId());
                    ratingDto.setHotel(hotelById);
                } catch (FeignException.NotFound ex) {
                    logger.error("Hotel not found with given id :{} {}", ratingDto.getHotelId(), ex.getMessage());
                    throw new ResourceNotFoundException(" Hotel not found with given id {}" + ratingDto.getHotelId());

                } catch (Exception ex) {
                    logger.error("Error fetching ratings or hotel details for userId: {}{}", ratingDto.getHotelId(), ex.getMessage());
                    throw new RuntimeException("Failed to fetch ratings or hotel details", ex);
                }
            }).toList();
            userDto.setRatings(ratingList);

        }
        return userDto;
    }

    public ExternalServiceClient getExternalServiceClient(String type) {
        return factory.getServiceClient(type);
    }
}
