package com.example.blogapp.DTOs;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentPostDTO {
    String commentString;
    List<String> attachments;
    Integer blogId;
}
