package com.example.blogapp.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BlogLikePostDTO {
    Integer likerId;
    Integer blogId;
}
