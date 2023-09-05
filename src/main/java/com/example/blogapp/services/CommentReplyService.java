package com.example.blogapp.services;

import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.entities.CommentReplyEntity;

import java.util.List;

public interface CommentReplyService {
    List<CommentEntity> getRepliesByCommentId(int blogId, int commentId);

    CommentReplyEntity addReply(int blogId, int commentId, CommentEntity commentEntity);
}
