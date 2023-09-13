package com.example.blogapp.repositories;

import com.example.blogapp.entities.BlogReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogReportRepository extends JpaRepository<BlogReportEntity, Integer> {
}
