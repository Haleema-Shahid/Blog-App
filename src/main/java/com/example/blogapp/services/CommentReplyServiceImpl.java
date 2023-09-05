package com.example.blogapp.services;

import com.example.blogapp.DTOs.CommentPostDTO;
import com.example.blogapp.entities.CommentEntity;
import com.example.blogapp.entities.CommentReplyEntity;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.BlogRepository;
import com.example.blogapp.repositories.CommentReplyRepository;
import com.example.blogapp.repositories.CommentRepository;
import com.example.blogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentReplyServiceImpl implements CommentReplyService{
    @Autowired
    CommentReplyRepository commentReplyRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<CommentEntity> getRepliesByCommentId(int blogId, int commentId) {
        List<CommentReplyEntity> commentReplyEntities = commentReplyRepository.findAllByCommentId(commentId).orElseThrow(()-> new RuntimeException("Unable to fetch replies for comment!"));
        List<CommentEntity> replies = commentReplyEntities.stream().map(CommentReplyEntity::getCommentByCommentId).toList();
        return replies;
    }

    @Override
    public CommentReplyEntity addReply(int blogId, int commentId, CommentEntity addedReply) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("Comment not found."));
        CommentEntity commentReply = commentRepository.save(addedReply);

        Integer replierId = blogRepository.getUserIdByBlogId(blogId).orElseThrow(()->new RuntimeException("UserId not found from blogId!"));
        UserEntity replier = userRepository.findById(replierId).orElseThrow(()->new RuntimeException("User not found against userId!"));
        CommentReplyEntity reply = new CommentReplyEntity();
        reply.setCommentByCommentId(comment);
        reply.setCommentByReplyId(commentReply);
        reply.setUserByReplierId(replier);

        CommentReplyEntity replyEntity = commentReplyRepository.save(reply);
        return replyEntity;
    }
}
