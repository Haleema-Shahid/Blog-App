package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.SuggestionPostDTO;
import com.example.blogapp.entities.SuggestionEntity;
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



    @GetMapping("/blog/suggestions/{blogId}")
    public ResponseEntity<List<SuggestionEntity>> getAllSuggestionsByBlogId(@PathVariable int blogId) {
        try {
            List<SuggestionEntity> suggestions = suggestionService.getAllSuggestionsByBlogId(blogId);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}/suggestions")
    public ResponseEntity<List<SuggestionEntity>> getAllSuggestionsByUserId(@PathVariable int userId) {
        try {
            List<SuggestionEntity> suggestions = suggestionService.getAllSuggestionsByUserId(userId);
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


    @PostMapping("/suggestion/{id}")
    public ResponseEntity<SuggestionEntity> editSuggestion(@PathVariable int blogId, int suggestionId, @RequestBody SuggestionPostDTO suggestionDTO) {
        try {
            String suggestionString = suggestionDTO.getSuggestionString();

            SuggestionEntity suggestion = suggestionService.editSuggestion(suggestionId, suggestionString);
            return new ResponseEntity<>(suggestion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new SuggestionEntity(), HttpStatus.NOT_FOUND);
        }
    }


}
