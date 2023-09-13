package com.example.blogapp.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class UserVerificationDTO {
    String code;
    @JsonProperty("email")
    String userEmail;
}
