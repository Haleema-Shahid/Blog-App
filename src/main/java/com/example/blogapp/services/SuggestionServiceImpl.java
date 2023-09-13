package com.example.blogapp.services;

import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.SuggestionEntity;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.BlogRepository;
import com.example.blogapp.repositories.SuggestionRepository;
import com.example.blogapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;

    private final UserRepository userRepository;

    private final BlogRepository blogRepository;

    @Override
    public List<SuggestionEntity> getAllSuggestionsByBlogId(int blogId) throws Exception {

        BlogEntity blog  = blogRepository.findById(blogId).orElseThrow(()->new Exception("Blog against blogid not found."));
        List<SuggestionEntity> suggestions = suggestionRepository.findAllByBlogByBlogId(blog).orElseThrow(
                () -> new Exception("Suggestions not found.")
        );

        return suggestions;

    }

    @Override
    public List<SuggestionEntity> getAllSuggestionsByUserId(int userId) throws Exception {
        UserEntity suggester = userRepository.findById(userId).orElseThrow(() -> new Exception("Suggestions not found."));
        List<SuggestionEntity> suggestions = suggestionRepository.findAllByUserBySuggesterId(suggester).orElseThrow(
                () -> new Exception("Suggestions not found.")
        );
        return suggestions;
    }

    @Override
    public SuggestionEntity addSuggestion(int blogId, int suggesterId, String suggestion) {

        UserEntity user = userRepository.findById(suggesterId).orElseThrow(()-> new RuntimeException("User not found against userId."));
        BlogEntity blog = blogRepository.findById(blogId).orElseThrow(()-> new RuntimeException("User not found against userId."));
        SuggestionEntity suggestionEntity = new SuggestionEntity();
        suggestionEntity.setSuggestion(suggestion);
        suggestionEntity.setUserBySuggesterId(user);
        suggestionEntity.setBlogByBlogId(blog);
        //fix this boolean/tinyint/byte issue
        suggestionEntity.setIsRejected(false);
        SuggestionEntity addedSuggestion = suggestionRepository.save(suggestionEntity);
        return addedSuggestion;
    }

    @Override
    public SuggestionEntity editSuggestion(int suggestionId, String suggestionString) {
        SuggestionEntity suggestion = suggestionRepository.findById(suggestionId).orElseThrow(()->new RuntimeException("Suggestion not found!"));
        suggestion.setSuggestion(suggestionString);
        return suggestionRepository.save(suggestion);
    }

}
