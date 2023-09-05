package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.CommentPostDTO;
import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.entities.CommentReplyEntity;
import com.example.blogapp.services.CommentReplyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentReplyController {
    @Autowired
    CommentReplyService commentReplyService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/blog/{blogId}/comment/{commentId}/replies")
    public ResponseEntity<List<CommentEntity>> getRepliesByCommentId(@PathVariable int blogId, @PathVariable int commentId){
        try{
        List<CommentEntity> replies = commentReplyService.getRepliesByCommentId(blogId, commentId);
        return new ResponseEntity<>(replies, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/blog/{blogId}/comment/{commentId}/reply/")
    public ResponseEntity<CommentReplyEntity> addReply(@PathVariable int blogId, @PathVariable int commentId, @RequestBody CommentPostDTO replyDTO){
        try{
            CommentEntity reply = modelMapper.map(replyDTO, CommentEntity.class);
            CommentReplyEntity replyEntity = commentReplyService.addReply(blogId, commentId, reply);
            return new ResponseEntity<>(replyEntity, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new CommentReplyEntity(), HttpStatus.BAD_REQUEST);
        }
    }





}
