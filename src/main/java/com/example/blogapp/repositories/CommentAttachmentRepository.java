package com.example.blogapp.repositories;

import com.example.blogapp.entities.CommentAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentAttachmentRepository extends JpaRepository<CommentAttachmentEntity, Integer> {
}
