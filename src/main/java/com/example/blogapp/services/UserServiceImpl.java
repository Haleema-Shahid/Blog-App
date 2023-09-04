package com.example.blogapp.services;


import com.example.blogapp.config.JWTService;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;

    @Override
    public Optional<UserEntity> getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Optional<UserEntity> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public String getUser(String username, String password) {
        System.out.println("entered getUser");
        try {
            UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user exists under this email."));
            if (user == null) {
                System.out.println("user is null");
                return null;
            }
            System.out.println("user fetched: " + user.getEmail());
            String encodedPassword = user.getPassword();
            if (passwordEncoder.matches(password, encodedPassword)) {
                String token = jwtService.generateToken(user);
                System.out.println(token);
                return token;
            } else throw new UsernameNotFoundException("Incorrect Password");
        } catch (UsernameNotFoundException e) {
            return e.getMessage();
        }

    }
}
