package com.example.blogapp.DTOs;

import lombok.*;

@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class SuggestionPostDTO {
    Integer blogId;
    String suggestionString;
    Integer suggesterId;
}
