package com.alam.rating.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ratings")
public class Rating {
    @Id
    private String ratingId;
    private String userId;
    private String hotelId;
    private double rating;
    private String feedback;
}
