package com.example.blogapp.repositories;

import com.example.blogapp.entities.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    Optional<Integer> getUserIdById(int blogId);

    Optional<BlogEntity> findByIdAndIsHidden(int id, Byte isHidden);

    List<BlogEntity> findAllByIsHidden(byte isHidden);
}
