package com.example.blogapp.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SuggestionDTO {
    int id;
    String suggestion;
    UserWithSuggestionDTO suggester;
    int blogId;
    boolean isRejected;
}
