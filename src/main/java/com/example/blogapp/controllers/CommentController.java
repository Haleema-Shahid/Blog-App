package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.CommentPostDTO;
import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin("http://localhost:3000")

public class CommentController {
    private final CommentService commentService;


    private final ModelMapper modelMapper;

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
            int blogId = id;
            CommentEntity comment = modelMapper.map(commentPostDTO, CommentEntity.class);
            String response = commentService.editComment(id,blogId, comment);
            return new ResponseEntity<>(response, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable int id){
        try{
            int commentId = id;
            String response = commentService.likeComment(commentId);
            return new ResponseEntity<>(response, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{id}/unlike")
    public ResponseEntity<String> unLikeComment(@PathVariable int id){
        try{
            int commentId = id;
            String response = commentService.unLikeComment(commentId);
            return new ResponseEntity<>(response, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<String> reportComment(@PathVariable int id){
        try{
            int commentId = id;
            String response = commentService.reportComment(commentId);
            return new ResponseEntity<>(response, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable int id){
        try{
            int commentId = id;
            String response = commentService.deleteComment(commentId);
            return new ResponseEntity<>(response, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
