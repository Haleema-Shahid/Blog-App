package com.example.blogapp.DTOs;

import com.example.blogapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfilePostDTO implements Serializable {
    @JsonProperty("firstname")
    String firstName;
    @JsonProperty("lastname")
    String lastName;
    String email;
    String password;
    Role role;
    @JsonProperty("dob")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    Date dateOfBirth;
    String bio;
    @JsonProperty("pfp")
    String profilePicture;
}
