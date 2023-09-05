package com.example.blogapp.services;

import com.example.blogapp.entities.SuggestionEntity;

import java.util.List;
import java.util.Optional;

public interface SuggestionService {
    List<SuggestionEntity> getAllSuggestionsByBlogId(int blogId);

    List<SuggestionEntity> getAllSuggestionsByUserId(int userId);

    SuggestionEntity addSuggestion(int blogId, int suggesterId, String suggestion);

    SuggestionEntity editSuggestion(int suggestionId, String suggestionString);
}
