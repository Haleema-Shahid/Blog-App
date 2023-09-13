package com.example.blogapp.services;

import com.example.blogapp.DTOs.UserLoginDTO;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.UnsupportedEncodingException;
import java.util.Optional;


public interface UserService {
    Optional<UserEntity> getUserByEmailAndPassword(String email, String password);
    Optional<UserEntity> getUserById(Integer id);
    UserLoginDTO getUser(String email, String password);
    String register(UserEntity user) throws MessagingException, UnsupportedEncodingException;
    String verifyUser(String email, String code);

    String editProfile(UserEntity user) throws Exception;
}
