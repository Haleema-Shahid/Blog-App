package com.example.blogapp.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class BlogPostDTO {
    String content;
    List<String> attachments;
}
