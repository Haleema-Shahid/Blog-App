package com.example.blogapp.DTOs;

import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuggestionPostDTO {
    String suggestionString;
    Integer suggesterId;
}
