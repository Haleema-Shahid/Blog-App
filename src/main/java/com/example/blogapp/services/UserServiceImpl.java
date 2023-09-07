package com.example.blogapp.services;


import com.example.blogapp.config.JWTService;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private JavaMailSender javaMailSender;

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

    public String register(UserEntity user) {

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setIsEnabled(false);

        try {

            userRepository.save(user);
            sendVerificationEmail(user, randomCode);
            return "email sent";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "exxception thrown";
        }
    }

    @Override
    public String verifyUser(String email, String code) {
        try {
            UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            if (user.getVerificationCode().equals(code)) {
                user.setIsEnabled(true);
                userRepository.save(user);
                return "enabled";
            } else {
                return "wrong code";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void verifyEmail(UserEntity userEntity, String email) throws MessagingException, UnsupportedEncodingException {
       if(!userEntity.getEmail().equals(email)){
           String randomCode = RandomString.make(64);
           userEntity.setVerificationCode(randomCode);
           userEntity.setIsEnabled(false);
           sendVerificationEmail(userEntity, email);
       }
    }

    @Override
    public String editProfile(UserEntity user) {
        try {
            UserEntity userEntity = userRepository.findByEmail(user.getEmail()).orElseThrow(()->new UsernameNotFoundException("User doesnt exist"));
            userEntity.setFirstname(user.getFirstname());
            userEntity.setLastname(user.getLastname());
            userEntity.setPassword(user.getPassword());
            userEntity.setEmail(user.getEmail());
            userEntity.setDob(user.getDob());
            userEntity.setBio(user.getBio());
            userEntity.setProfileImage(user.getProfileImage());
            userRepository.save(userEntity);
            return "update successful!";
        } catch (Exception e) {
            return "Failed!";
        }
    }

    private void sendVerificationEmail(UserEntity user, String code)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "haleema.shahid@devsinc.com";
        String senderName = "Blog App";
        String subject = "Blog App Verification Code";
        String content = "Dear [[name]],<br>"
                + "Here is your verification code:<br>"
                + "<h3>[[code]]</h3>"
                + "Thank you,<br>"
                + "Blog App";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname());


        content = content.replace("[[code]]", code);

        helper.setText(content, true);

        javaMailSender.send(message);

    }
}
