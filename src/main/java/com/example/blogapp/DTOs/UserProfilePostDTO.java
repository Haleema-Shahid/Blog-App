package com.example.blogapp.DTOs;

import com.example.blogapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class UserProfilePostDTO implements Serializable {
    Integer id;
    String firstname;
    String lastname;
    String email;
    String password;
    String bio;
}
