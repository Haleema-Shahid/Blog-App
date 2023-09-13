package com.example.blogapp.DTOs;

import com.example.blogapp.entities.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
//@NoArgsConstructor
public class BlogDTO {
    Integer id;
    String title;
    String content;
    List<String> attachments;
    Integer likes;
    Integer comments;
    UserWithBlogDTO user;
    List<CommentDTO> blogComments;
}
