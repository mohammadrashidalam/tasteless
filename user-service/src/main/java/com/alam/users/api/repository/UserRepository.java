package com.alam.users.api.repository;

import com.alam.users.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    //if we want to implement  any custom method or any query.
    //write
}
