package com.example.blogapp.DTOs;

import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginPostDTO {
    String email;
    String password;
}
