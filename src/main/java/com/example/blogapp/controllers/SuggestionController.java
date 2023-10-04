package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.*;
import com.example.blogapp.entities.SuggestionEntity;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.services.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/suggestions")
@CrossOrigin("http://localhost:3000")
public class SuggestionController {

    private final SuggestionService suggestionService;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;



    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<SuggestionDTO>> getAllSuggestionsByBlogId(@PathVariable int blogId) {
        try {
            List<SuggestionDTO> suggestions = suggestionService.getAllSuggestionsByBlogId(blogId);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SuggestionDTO>> getAllSuggestionsByUserId(@PathVariable int userId) {
        try {
            List<SuggestionDTO> suggestions = suggestionService.getAllSuggestionsByUserId(userId);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<SuggestionEntity> addSuggestion(@RequestBody SuggestionPostDTO suggestionDTO) {
        try {
            Integer suggesterId = suggestionDTO.getSuggesterId();
            String suggestionString = suggestionDTO.getSuggestionString();
            Integer blogId = suggestionDTO.getBlogId();

            SuggestionEntity suggestion = suggestionService.addSuggestion(blogId, suggesterId, suggestionString);
            return new ResponseEntity<>(suggestion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new SuggestionEntity(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{id}/reply")
    public ResponseEntity<String> replyToSuggestion(@PathVariable int id, @RequestBody SuggestionReplyPostDTO replyDTO) {
        try {

            Integer suggesterId = replyDTO.getSuggesterId();
            String suggestionString = replyDTO.getSuggestion();
            Integer blogId = replyDTO.getBlogId();
            Integer replyTo = replyDTO.getReplyTo();

            System.out.println("suggesterId: "+suggesterId);
            System.out.println("replyTo: "+replyTo);
            SuggestionEntity suggestion = suggestionService.replyToSuggestion(blogId, suggesterId, suggestionString, replyTo);
            return new ResponseEntity<>("replied successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectSuggestion(@PathVariable int id) {
        try {
            suggestionService.rejectSuggestion(id);
            return new ResponseEntity<>("rejected successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<SuggestionEntity> editSuggestion(@PathVariable int id, @RequestBody SuggestionEditDTO suggestionDTO) {
        try {
            String suggestionString = suggestionDTO.getSuggestion();

            SuggestionEntity suggestion = suggestionService.editSuggestion(id, suggestionString);
            return new ResponseEntity<>(suggestion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new SuggestionEntity(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<SuggestionDTO>> getReplies(@PathVariable int id){
        try{
            Integer replyTo = id;
            List<SuggestionEntity> replies = suggestionService.getRepliesOfSuggestion(id);

            List<SuggestionDTO> suggestionDTOS = new ArrayList<>();
            for(SuggestionEntity suggestion: replies){

                UserEntity user = suggestion.getUserBySuggesterId();

                SuggestionDTO dto = new SuggestionDTO();
                UserWithSuggestionDTO userWithSuggestionDTO = modelMapper.map(user, UserWithSuggestionDTO.class);
                dto.setSuggester(userWithSuggestionDTO);
                dto.setSuggestion(suggestion.getSuggestion());
                dto.setRejected(suggestion.getIsRejected() != 0);
                dto.setBlogId(suggestion.getBlogByBlogId().getId());
                dto.setId(suggestion.getId());

                suggestionDTOS.add(dto);
            }

            return new ResponseEntity<>(suggestionDTOS, HttpStatus.OK);

        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

}
