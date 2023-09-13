package com.example.blogapp.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class UserWithBlogDTO {
    int id;
    String firstname;
    String lastname;
}
