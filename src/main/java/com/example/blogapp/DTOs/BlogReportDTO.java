package com.example.blogapp.DTOs;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BlogReportDTO {
    Integer blogId;
    Integer reporterId;
}
