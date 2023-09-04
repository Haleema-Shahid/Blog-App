package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.UserLoginPostDTO;
import com.example.blogapp.DTOs.UserSignupPostDTO;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.services.UserService;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginPostDTO user){

        String email = user.getEmail();
        String password = user.getPassword();
        String response = userService.getUser(email, password);
        if(response.equals("No user exists under this email.")){
            return new ResponseEntity<>("No user exists under this email.", HttpStatus.BAD_REQUEST);
        }
        else if(response.equals("Incorrect password")){
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/add-user")
    public UserEntity addUser(@RequestBody UserSignupPostDTO user){
        System.out.println("add-user api...");
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        System.out.println("mapped "+ userEntity.toString());
        return userEntity;
    }


}
