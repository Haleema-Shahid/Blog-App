package com.example.blogapp.repositories;

import com.example.blogapp.entities.CommentReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReplyEntity, Integer> {
    Optional<List<CommentReplyEntity>> findAllByCommentId(int commentId);
}
