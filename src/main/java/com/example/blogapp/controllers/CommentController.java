package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.CommentPostDTO;
import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CommentEntity> getComment(@PathVariable int id){
        try{
        CommentEntity comment = commentService.getComment(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(new CommentEntity(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> addComment(@RequestBody CommentPostDTO commentPostDTO){
        try{
        CommentEntity comment = modelMapper.map(commentPostDTO, CommentEntity.class);
        String response = commentService.addComment(comment);
        return new ResponseEntity<>(response, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<String> editComment(@PathVariable int id, @RequestBody CommentPostDTO commentPostDTO){
        try{
            int blogId = commentPostDTO.getBlogId();
            CommentEntity comment = modelMapper.map(commentPostDTO, CommentEntity.class);
            String response = commentService.editComment(id,blogId, comment);
            return new ResponseEntity<>(response, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
