package com.example.blogapp.DTOs;

import com.example.blogapp.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserVerificationGetDTO {
    String code;
    @JsonProperty("user_email")
    String userEmail;
}
