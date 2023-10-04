package com.example.blogapp.repositories;

import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    Optional<List<CommentEntity>> findCommentEntitiesByBlogByBlogId(BlogEntity blog);

    List<CommentEntity> findByReplyTo(int commentId);

    Optional<List<CommentEntity>> findAllByIsHiddenAndIsReported(byte b, byte b1);
}
