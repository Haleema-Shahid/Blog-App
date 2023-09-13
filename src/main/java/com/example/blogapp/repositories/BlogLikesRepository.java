package com.example.blogapp.repositories;

import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.BlogLikesEntity;
import com.example.blogapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BlogLikesRepository extends JpaRepository<BlogLikesEntity, Integer> {
    Optional<BlogLikesEntity> findByUserByLikerIdAndBlogByBlogId(UserEntity liker, BlogEntity blog);
}
