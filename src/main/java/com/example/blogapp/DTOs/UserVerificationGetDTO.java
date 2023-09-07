package com.example.blogapp.DTOs;

import com.example.blogapp.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserVerificationGetDTO {
    String code;
    @JsonProperty("user_email")
    String userEmail;
}
