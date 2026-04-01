package com.alam.hotels.api.repository;

import com.alam.hotels.api.entites.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {

    //create

    //getAll

    //getHotel
}
