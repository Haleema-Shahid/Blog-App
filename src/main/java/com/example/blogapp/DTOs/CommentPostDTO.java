package com.example.blogapp.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class CommentPostDTO {
    String commentString;
    List<String> attachments;
    Integer blogId;
}
