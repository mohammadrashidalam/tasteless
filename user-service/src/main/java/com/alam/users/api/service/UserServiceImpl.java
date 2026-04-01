package com.alam.users.api.service;

import com.alam.users.api.dto.HotelDto;
import com.alam.users.api.dto.RatingDto;
import com.alam.users.api.dto.UserDto;
import com.alam.users.api.entities.User;
import com.alam.users.api.exception.ResourceNotFoundException;
import com.alam.users.api.repository.UserRepository;
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
public class UserServiceImpl implements UserService{
    private  UserRepository userRepository;
    private  ModelMapper modelMapper;
    private static final String URI="http://RATING-SERVICE/api/v1/rating/users/";
    private static final String URI2="http://HOTEL-SERVICE/api/v1/hotel/getHotelById/";
    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);
    private RestTemplate restTemplate;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
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
        UserDto userDto1=modelMapper.map(saveUser, UserDto.class);
        userDto1.setUserId(user.getUuid());
        return userDto1;
    }

    /**.
     * @return  List<UserDto>
     */
    @Override
    public List<UserDto> getAllUser() {
        List<User> listOfUser = userRepository.findAll();
        List<UserDto> dtoList = listOfUser.stream()
                .map(user -> {
                    UserDto userDto=modelMapper.map(user, UserDto.class);
                    userDto.setUserId(user.getUuid());
                    RatingDto[] forObject = restTemplate.getForObject(URI+user.getUuid(), RatingDto[].class);
                    LOGGER.info("{}",forObject);
                    List<RatingDto> ratings = Arrays.stream(forObject).toList();
                    List<RatingDto> ratingList = ratings.stream().map(ratingDto -> {
                        //http://localhost:8082/api/v1/hotel/getHotelById/db25de89-8d05-4f97-b32c-e0fc32c31ac0
                        LOGGER.info("{}", ratingDto.getHotelId());
                        ResponseEntity<HotelDto> forEntity = restTemplate
                                .getForEntity(URI2 + ratingDto.getHotelId(), HotelDto.class);
                        HotelDto hotelDto = forEntity.getBody();
                        LOGGER.info("response status code {}", forEntity.getStatusCode());
                        ratingDto.setHotel(hotelDto);
                        return ratingDto;
                    }).toList();
                    userDto.setRatings(ratingList);
                    return userDto;
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
                .orElseThrow(()->new ResourceNotFoundException("User not found given userId "+userId));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setUserId(user.getUuid());
        //fetch rating if the above user  from Rating Service
        //http://localhost:8083/api/v1/rating/users/69d2af3e-0be1-451a-aa70-aa776e8d6235
        RatingDto[] forObject = restTemplate.getForObject(URI+user.getUuid(), RatingDto[].class);
        LOGGER.info("{}",forObject);
        List<RatingDto> ratings = Arrays.stream(forObject).toList();
        List<RatingDto> ratingList = ratings.stream().map(ratingDto -> {
            //http://localhost:8082/api/v1/hotel/getHotelById/db25de89-8d05-4f97-b32c-e0fc32c31ac0
            LOGGER.info("{}", ratingDto.getHotelId());
            ResponseEntity<HotelDto> forEntity = restTemplate
                    .getForEntity(URI2 + ratingDto.getHotelId(), HotelDto.class);
            HotelDto hotelDto = forEntity.getBody();
            LOGGER.info("response status code {}", forEntity.getStatusCode());
            ratingDto.setHotel(hotelDto);
            return ratingDto;
        }).toList();
        userDto.setRatings(ratingList);
        return userDto;
    }
}
