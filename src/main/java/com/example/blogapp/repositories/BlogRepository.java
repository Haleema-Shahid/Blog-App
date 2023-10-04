package com.example.blogapp.repositories;

import com.example.blogapp.entities.BlogEntity;
import com.example.blogapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    Optional<Integer> getUserIdById(int blogId);

    Optional<BlogEntity> findByIdAndIsHidden(int id, Byte isHidden);

    List<BlogEntity> findAllByIsHidden(byte isHidden);

    Optional<List<BlogEntity>> findAllByUserByUserId(UserEntity user);

    Optional<List<BlogEntity>> findAllByUserByUserIdAndIsApproved(UserEntity user, byte b);

    Optional<BlogEntity> findByIdAndIsHiddenAndIsApproved(int id, byte b, byte b1);

    List<BlogEntity> findAllByIsHiddenAndIsApproved(byte b, byte b1);

    Optional<BlogEntity> findByIdAndIsHiddenAndIsReported(int id, byte b, byte b1);

    List<BlogEntity> findAllByIsHiddenAndIsReported(byte b, byte b1);
}
