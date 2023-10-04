package com.example.blogapp.services;

import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.repositories.BlogRepository;
import com.example.blogapp.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;

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
        commentEntity.setCommentAttachmentsById(updated.getCommentAttachmentsById());
        commentRepository
                .save(commentEntity);
        return "updated succesfully!";
    }

    @Override
    public String likeComment(int commentId) throws Exception {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()->new Exception("comment not found"));
        comment.setLikes(comment.getLikes()+1);
        commentRepository.save(comment);
        return "liked comment successfully!";
    }

    @Override
    public String unLikeComment(int commentId) throws Exception {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()->new Exception("comment not found"));
        comment.setLikes(comment.getLikes()-1);
        commentRepository.save(comment);
        return "unliked comment successfully!";
    }

    @Override
    public String reportComment(int commentId) throws Exception {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()->new Exception("comment not found"));
        comment.setIsReported(true);
        commentRepository.save(comment);
        return "reported comment successfully!";
    }

    @Override
    public String deleteComment(int commentId) throws Exception {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()->new Exception("comment not found"));
        BlogEntity blog = comment.getBlogByBlogId();

        comment.setIsHidden(true);
        commentRepository.save(comment);
        blog.setComments(blog.getComments()-1);
        blogRepository.save(blog);
        return "reported comment successfully!";
    }

    @Override
    public List<CommentEntity> getReportedComments() throws Exception {
        return commentRepository.findAllByIsHiddenAndIsReported((byte)0, (byte)1).orElseThrow(()->new Exception("comments not found"));
    }
}

