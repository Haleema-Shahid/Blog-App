package com.example.blogapp.services;

import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.entities.CommentReplyEntity;
import com.example.blogapp.repositories.CommentReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentReplyServiceImpl implements CommentReplyService{
    @Autowired
    CommentReplyRepository commentReplyRepository;

    @Override
    public List<CommentEntity> getRepliesByCommentId(int blogId, int commentId) {
        List<CommentReplyEntity> commentReplyEntities = commentReplyRepository.findAllByCommentId(commentId).orElseThrow(()-> new RuntimeException("Unable to fetch replies for comment!"));
        List<CommentEntity> replies = commentReplyEntities.stream().map(CommentReplyEntity::getCommentByCommentId).toList();
        return replies;
    }
}
