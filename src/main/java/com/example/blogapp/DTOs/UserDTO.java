package com.example.blogapp.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    Integer id;
    String firstname;
    String lastname;
    String email;
    String bio;
    String verificationCode;
    Boolean isEnabled;
}
