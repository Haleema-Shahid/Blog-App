package com.example.blogapp.DTOs;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogPostDTO {
    String content;
    List<String> attachments;
}
