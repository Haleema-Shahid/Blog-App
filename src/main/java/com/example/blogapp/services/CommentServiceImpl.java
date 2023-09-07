package com.example.blogapp.services;

import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.repositories.BlogRepository;
import com.example.blogapp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BlogRepository blogRepository;

    @Override
    public CommentEntity getComment(int id) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(()->new RuntimeException("comment not found!"));
        return comment;
    }

    @Override
    public String addComment(CommentEntity comment) {
        CommentEntity commentEntity = commentRepository.save(comment);
        return "added successfully!";
    }

    @Override
    public String editComment(int id, int blogId, CommentEntity updated) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(()->new RuntimeException("Comment with this id doesnt exist"));
        commentEntity.setComment(updated.getComment());
        BlogEntity blog = blogRepository.findById(blogId).orElseThrow(()->new RuntimeException("Blog with this id doesnt exist"));
        commentEntity.setBlogByBlogId(blog);
        return "updated succesfully!";
    }

}

