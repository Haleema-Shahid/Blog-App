package com.example.blogapp.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class UserLoginDTO {
    String token;
    Integer id;
}
