package com.example.blogapp.services;

import com.example.blogapp.entities.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    CommentEntity getComment(int id);

    String addComment(CommentEntity comment);

    String editComment(int id, int blogId, CommentEntity comment);

    String likeComment(int commentId) throws Exception;
    String unLikeComment(int commentId) throws Exception;

    String reportComment(int commentId) throws Exception;

    String deleteComment(int commentId) throws Exception;

    List<CommentEntity> getReportedComments() throws Exception;
}
