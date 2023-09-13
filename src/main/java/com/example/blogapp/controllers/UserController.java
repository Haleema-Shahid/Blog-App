package com.example.blogapp.controllers;

import com.example.blogapp.DTOs.*;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = {"http://localhost:3000"},methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE},allowCredentials = "true", maxAge = 3600)

public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestBody UserLoginPostDTO user){
        String email = user.getEmail();
        String password = user.getPassword();
        System.out.println(email+" "+password);
        UserLoginDTO response = userService.getUser(email, password);
        if(response.getToken().equals("No user exists under this email.")){
            return new ResponseEntity<>(new UserLoginDTO(), HttpStatus.BAD_REQUEST);
        }
        else if(response.getToken().equals("Incorrect password")){
            return new ResponseEntity<>(new UserLoginDTO(), HttpStatus.BAD_REQUEST);
        }
        else if(response.getToken().equals("exception thrown")){
            return new ResponseEntity<>(new UserLoginDTO(), HttpStatus.BAD_REQUEST);
        }
        else if(response.getToken().equals("User is not enabled")){
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setToken("Redirect to email verification");
            return new ResponseEntity<>(userLoginDTO, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.OK);}
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id){
        try {
            UserEntity userEntity = userService.getUserById(id).orElseThrow(()-> new Exception("User against id not found"));
            UserDTO user = modelMapper.map(userEntity, UserDTO.class);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/add-user")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserSignupPostDTO user){
        try {
            System.out.println("add-user api...");
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());

            UserEntity userEntity = modelMapper.map(user, UserEntity.class);


            String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(encodedPassword);

            System.out.println("mapped " + userEntity.toString());
            userService.register(userEntity);

            UserDTO userDto = modelMapper.map(userEntity, UserDTO.class);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new UserDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    
    @PostMapping("/verification")
    public ResponseEntity<String> verifyUser(@RequestBody UserVerificationDTO userVerificationDTO){
       try{
           String email = userVerificationDTO.getUserEmail();
           String code = userVerificationDTO.getCode();
        String response = userService.verifyUser(email, code);
        return new ResponseEntity<>(response,HttpStatus.OK);}
       catch(Exception e){
           return new ResponseEntity<>(" ",HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> updateUser(@RequestBody UserProfilePostDTO userProfilePostDTO){
        UserEntity user = modelMapper.map(userProfilePostDTO, UserEntity.class);
        String response = null;
        try {
            response = userService.editProfile(user);
        } catch (Exception e) {
            response = e.getMessage();
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
