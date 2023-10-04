package com.example.blogapp.DTOs;

import com.example.blogapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class UserSignupPostDTO {
    @JsonProperty("firstname")
    String firstName;
    @JsonProperty("lastname")
    String lastName;
    String email;
    String password;

}
