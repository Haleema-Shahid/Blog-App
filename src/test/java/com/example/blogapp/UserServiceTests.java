package com.example.blogapp;

import com.example.blogapp.DTOs.UserLoginDTO;
import com.example.blogapp.config.JWTService;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.services.UserServiceImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private JWTService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage message;
    private UserEntity userEntityMock;

    @BeforeEach
    void setup() {
        userEntityMock = new UserEntity();
        userEntityMock.setId(21);
        userEntityMock.setEmail("hs@email.com");
        userEntityMock.setPassword("hmm");
        userEntityMock.setIsEnabled(true);
        userRepository.save(userEntityMock);
    }

    @DisplayName("validLogin test")
    @Test
    void validLoginTest() {
        String invalidEmail = "notexist@email.com";
        String validEmail = "hs@email.com";

        String validPassword = "hmm";
        String invalidPassword = "wrong";


        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.ofNullable(userEntityMock));
        when(jwtService.generateToken(userEntityMock)).thenReturn("my_token");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);


        UserLoginDTO user = userService.getUser(validEmail, validPassword);


        System.out.println("Token " + user.getToken());
        System.out.println("id " + user.getId());
        assertEquals("my_token", user.getToken());

    }

    @DisplayName("invalidLogin test")
    @Test
    void invalidLoginTest(){
        String invalidEmail = "notexist@email.com";
        String validEmail = "hs@email.com";

        String validPassword = "hmm";
        String invalidPassword = "wrong";


        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.ofNullable(userEntityMock));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);


        UserLoginDTO validUser = userService.getUser(validEmail, validPassword);

        assertEquals("incorrect credentials", validUser.getToken());




    }

    @DisplayName("Register User Test")
    @Test
    void registerUserTest() throws Exception {
        UserEntity userToRegister = new UserEntity();
        userToRegister.setFirstname("John");
        userToRegister.setLastname("Doe");
        userToRegister.setEmail("john@example.com");
        userToRegister.setPassword("password");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        String result = userService.register(userToRegister);

        assertEquals("email sent", result);
    }

    @DisplayName("Verify User Test")
    @Test
    void verifyUserTest() {
        String email = "hs@email.com";
        String validCode = "valid-code";
        String invalidCode = "invalid-code";

        userEntityMock.setVerificationCode(validCode);

        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(userEntityMock));

        // Test valid verification code
        String validResponse = userService.verifyUser(email, validCode);
        assertEquals("enabled", validResponse);

        // Test invalid verification code
        String invalidResponse = userService.verifyUser(email, invalidCode);
        assertEquals("not enabled", invalidResponse);
    }

    @DisplayName("Edit User Profile Test")
    @Test
    void editUserProfileTest() throws Exception {
        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(21);
        updatedUser.setFirstname("UpdatedFirstName");
        updatedUser.setLastname("UpdatedLastName");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("updatedPassword");

        when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.ofNullable(userEntityMock));
        when(passwordEncoder.matches(updatedUser.getPassword(), userEntityMock.getPassword())).thenReturn(true);
        when(javaMailSender.createMimeMessage()).thenReturn(message);

        String result = userService.editProfile(updatedUser);

        assertEquals("update successful!", result);
    }

}
