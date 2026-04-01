package com.alam.users.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private String userName;
    private String email;
    private String aboutUs;
    private List<RatingDto> ratings =new ArrayList<RatingDto>();
}
