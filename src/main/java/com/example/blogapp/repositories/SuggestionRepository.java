package com.example.blogapp.repositories;

import com.example.blogapp.entities.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuggestionRepository extends JpaRepository<SuggestionEntity, Integer> {
    Optional<List<SuggestionEntity>> findAllByBlogId(int blogId);

    Optional<List<SuggestionEntity>> findAllBySuggesterId(int userId);
}
