package com.example.blogapp.DTOs;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class CommentPostDTO {
    String comment;
    List<String> attachments;
    Byte isReported;
    Byte isHidden;
    Integer likes;
    Integer replies;
    Integer replyTo;
}
