package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.CommentDTO;
import com.example.blogapp.DTOs.CommentPostDTO;
import com.example.blogapp.DTOs.UserDTO;
import com.example.blogapp.entities.CommentAttachmentEntity;
import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/reported")
    public ResponseEntity<List<CommentDTO>> getReportedComments(){
        try{
            List<CommentEntity> commentEntities = commentService.getReportedComments();

            List<CommentDTO> commentDTOS = new ArrayList<>();
            for(CommentEntity comment: commentEntities){
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                UserEntity user = comment.getUserByCommenterId();
                UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                commentDTO.setCommenter(userDTO);

                Set<CommentAttachmentEntity> attachmentEntitySet = comment.getCommentAttachmentsById();
                List<String> attachments = new ArrayList<>();
                for(CommentAttachmentEntity attachmentEntity: attachmentEntitySet){
                    attachments.add(attachmentEntity.getAttachment());
                }
                commentDTO.setAttachments(attachments);
                commentDTOS.add(commentDTO);
            }
            return new ResponseEntity<>(commentDTOS, HttpStatus.OK);

        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}
