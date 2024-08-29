package com.evcharging.station.DTO;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int userId;


   private String firstName;


    private String lastName;

    private  String emailId;

    private  String mobileNumber;
}
