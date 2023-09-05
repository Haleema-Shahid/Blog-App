package com.example.blogapp.controllers;

import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.entities.CommentReplyEntity;
import com.example.blogapp.services.CommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentReplyController {
    @Autowired
    CommentReplyService commentReplyService;

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

    


}
