package com.alam.users.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name="user_id")
    private String uuid;
    @Column(name="user_name",length = 20)
    private String username;
    @Column(name="email",length = 30)
    private String email;
    @Column(name="about_us", length = 100)
    private String aboutUs;




}
