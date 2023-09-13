package com.example.blogapp.DTOs;

import com.example.blogapp.entities.BlogEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class BlogsGetDTO {
    List<BlogDTO> blogs;
}
