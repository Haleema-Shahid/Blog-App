package com.example.blogapp.repositories;

import com.example.blogapp.entities.BlogAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogAttachmentRepository extends JpaRepository<BlogAttachmentEntity, Integer> {
}
