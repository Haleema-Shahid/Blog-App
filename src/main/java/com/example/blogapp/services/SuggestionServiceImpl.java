package com.example.blogapp.services;

import com.example.blogapp.DTOs.SuggestionDTO;
import com.example.blogapp.DTOs.UserWithSuggestionDTO;
import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.SuggestionEntity;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.BlogRepository;
import com.example.blogapp.repositories.SuggestionRepository;
import com.example.blogapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;

    private final UserRepository userRepository;

    private final BlogRepository blogRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<SuggestionDTO> getAllSuggestionsByBlogId(int blogId) throws Exception {

        BlogEntity blog  = blogRepository.findById(blogId).orElseThrow(()->new Exception("Blog against blogid not found."));
        List<SuggestionEntity> suggestions = suggestionRepository.findAllByBlogByBlogId(blog).orElseThrow(
                () -> new Exception("Suggestions not found.")
        );
        List<SuggestionDTO> suggestionDTOS = new ArrayList<>();
        for(SuggestionEntity suggestion: suggestions){
            if(suggestion.getIsRejected() == 0 && suggestion.getReplyTo()==null) {
                UserEntity user = suggestion.getUserBySuggesterId();
                UserWithSuggestionDTO suggester = modelMapper.map(user, UserWithSuggestionDTO.class);
                SuggestionDTO suggestionDTO = new SuggestionDTO();
                suggestionDTO.setSuggester(suggester);
                suggestionDTO.setBlogId(blogId);
                suggestionDTO.setId(suggestion.getId());
                suggestionDTO.setSuggestion(suggestion.getSuggestion());
                suggestionDTOS.add(suggestionDTO);
            }
        }

        return suggestionDTOS;

    }

    @Override
    public List<SuggestionDTO> getAllSuggestionsByUserId(int userId) throws Exception {
        UserEntity suggester = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found."));
        List<SuggestionEntity> suggestions = suggestionRepository.findAllByUserBySuggesterId(suggester)
                .orElseThrow(() -> new Exception("Suggestions not found."));

        List<SuggestionDTO> suggestionDTOs = new ArrayList<>();

        for (SuggestionEntity suggestion : suggestions) {
            SuggestionDTO suggestionDTO = new SuggestionDTO();
            suggestionDTO.setId(suggestion.getId());
            suggestionDTO.setSuggestion(suggestion.getSuggestion());
            suggestionDTO.setBlogId(suggestion.getBlogByBlogId().getId());
            suggestionDTO.setRejected(suggestion.getIsRejected() != 0);
            // You may need to create and set a UserWithSuggestionDTO object for suggester here.
            // suggestionDTO.setSuggester(...);
            suggestionDTOs.add(suggestionDTO);
        }

        return suggestionDTOs;
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

    @Override
    public SuggestionEntity replyToSuggestion(Integer blogId, Integer suggesterId, String suggestionString, Integer replyTo) {
        UserEntity user = userRepository.findById(suggesterId).orElseThrow(()-> new RuntimeException("User not found against userId."));
        BlogEntity blog = blogRepository.findById(blogId).orElseThrow(()-> new RuntimeException("Blog not found against blogId."));
        SuggestionEntity suggestionEntity = new SuggestionEntity();
        suggestionEntity.setSuggestion(suggestionString);
        suggestionEntity.setUserBySuggesterId(user);
        suggestionEntity.setBlogByBlogId(blog);
        //fix this boolean/tinyint/byte issue
        suggestionEntity.setIsRejected(false);
        suggestionEntity.setReplyTo(replyTo);
        return suggestionRepository.save(suggestionEntity);
    }

    @Override
    public void rejectSuggestion(int id) throws Exception {
        SuggestionEntity suggestion = suggestionRepository.findById(id).orElseThrow(()->new Exception("Suggestion not found"));
        suggestion.setIsRejected(true);
        suggestionRepository.save(suggestion);
    }

    @Override
    public List<SuggestionEntity> getRepliesOfSuggestion(int id) throws Exception {
        return suggestionRepository.findByReplyTo(id).orElseThrow(()->new Exception("Suggestions not found"));
    }

}
