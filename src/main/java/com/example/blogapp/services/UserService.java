package com.example.blogapp.services;

import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;


public interface UserService {
    Optional<UserEntity> getUserByEmailAndPassword(String email, String password);
    Optional<UserEntity> getUserById(Integer id);
    String getUser(String email, String password);
    String register(UserEntity user);
    String verifyUser(String email, String code);

}
