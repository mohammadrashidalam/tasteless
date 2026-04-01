package com.alam.hotels.api.service;

import com.alam.hotels.api.dto.HotelDto;
import com.alam.hotels.api.entites.Hotel;
import com.alam.hotels.api.exception.ResourceNotFoundException;
import com.alam.hotels.api.repository.HotelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private HotelRepository hotelRepository;
    private ModelMapper modelMapper;

    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * @param hotelDto: hotelDto
     * @return HotelDto
     */
    @Override
    public HotelDto saveHotelDetails(HotelDto hotelDto) {
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        Hotel saveHotelDetails = hotelRepository.save(hotel);
        hotelDto = modelMapper.map(saveHotelDetails, HotelDto.class);
        return hotelDto;
    }

    /**
     * @param id:id
     * @return HotelDto
     */
    @Override
    public HotelDto findHotelById(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with given id :- " +id));
        return modelMapper.map(hotel, HotelDto.class);
    }

    /**
     * @return List<HotelDto>: List</HotelDto>
     */
    @Override
    public List<HotelDto> findAllHotels() {
        List<Hotel> listOfHotels = hotelRepository.findAll();
        return listOfHotels.stream().map(hotel ->
                modelMapper.map(hotel, HotelDto.class)
        ).toList();
    }
}
