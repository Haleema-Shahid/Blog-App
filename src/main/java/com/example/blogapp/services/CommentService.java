package com.example.blogapp.services;

import com.example.blogapp.entities.CommentEntity;

public interface CommentService {

    CommentEntity getComment(int id);

    String addComment(CommentEntity comment);

    String editComment(int id, int blogId, CommentEntity comment);
}
