package com.example.blogapp.services;

import com.example.blogapp.entities.CommentEntity;

import java.util.List;

public interface CommentReplyService {
    List<CommentEntity> getRepliesByCommentId(int blogId, int commentId);
}
