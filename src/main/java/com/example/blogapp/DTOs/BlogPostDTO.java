package com.example.blogapp.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class BlogPostDTO {
    @JsonProperty("user_id")
    Integer userId;
    String title;
    String content;
    Integer likes;
    Integer comments;
    @JsonProperty("is_reported")
    Byte isReported;
    @JsonProperty("is_approved")
    Byte isApproved;
    @JsonProperty("is_hidden")
    Byte isHidden;
    List<String> attachments;

}
