package com.example.blogapp.services;

import com.example.blogapp.DTOs.SuggestionDTO;
import com.example.blogapp.entities.SuggestionEntity;

import java.util.List;

public interface SuggestionService {
    List<SuggestionDTO> getAllSuggestionsByBlogId(int blogId) throws Exception;

    List<SuggestionDTO> getAllSuggestionsByUserId(int userId) throws Exception;

    SuggestionEntity addSuggestion(int blogId, int suggesterId, String suggestion);

    SuggestionEntity editSuggestion(int suggestionId, String suggestionString);

    SuggestionEntity replyToSuggestion(Integer blogId, Integer suggesterId, String suggestionString, Integer replyTo);

    void rejectSuggestion(int id) throws Exception;

    List<SuggestionEntity> getRepliesOfSuggestion(int id) throws Exception;
}
