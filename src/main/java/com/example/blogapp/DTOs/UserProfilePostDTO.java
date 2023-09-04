package com.example.blogapp.DTOs;

import com.example.blogapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserProfilePostDTO {
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
    List<String> changedFields;
}
