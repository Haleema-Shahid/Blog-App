package com.example.blogapp.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SuggestionReplyPostDTO {
    String suggestion;
    Integer suggesterId;
    int blogId;
    Integer replyTo;
}
