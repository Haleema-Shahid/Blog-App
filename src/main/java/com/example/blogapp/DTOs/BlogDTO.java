package com.example.blogapp.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class BlogDTO {
    String content;
    List<String> attachments;
    Integer likes;
    Integer Comments;

}
