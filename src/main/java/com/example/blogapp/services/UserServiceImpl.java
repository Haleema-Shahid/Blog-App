package com.example.blogapp.services;


import com.example.blogapp.DTOs.UserLoginDTO;
import com.example.blogapp.config.JWTService;
import com.example.blogapp.entities.UserEntity;
import com.example.blogapp.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public UserLoginDTO getUser(String username, String password) {
        System.out.println("entered getUser");
        try {
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user exists under this email."));
            if (user == null) {
                System.out.println("user is null");
                return userLoginDTO;
            }
            System.out.println("user fetched: " + user.getEmail());
            String encodedPassword = user.getPassword();
            System.out.println("encoded: " + encodedPassword);
            if (passwordEncoder.matches(password, encodedPassword) ) {
                System.out.println("matched");
                if(!user.isEnabled()){
                    userLoginDTO.setId(-1);
                    userLoginDTO.setToken("User is not enabled");
                    return userLoginDTO;
                }
                String token = jwtService.generateToken(user);
                System.out.println(token);
                userLoginDTO.setToken(token);
                userLoginDTO.setId(user.getId());
                return userLoginDTO;
            } else {
                userLoginDTO.setId(-2);
                userLoginDTO.setToken("incorrect password");
                return userLoginDTO;
            }
        } catch (UsernameNotFoundException e) {
            System.out.println("caught exception");
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setToken("caught exception");
            userLoginDTO.setId(-1);
            return userLoginDTO;
        }
    }

    public String register(UserEntity user) throws MessagingException, UnsupportedEncodingException {

        String randomCode = RandomString.make(16);
        user.setVerificationCode(randomCode);
        user.setIsEnabled(false);


        userRepository.save(user);
        sendVerificationEmail(user, randomCode);
        return "email sent";

    }

    @Override
    public String verifyUser(String email, String code) {
        try {
            System.out.println("code is "+code);
            UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found."));

            if(user.getVerificationCode().equals(code)){
                System.out.println("Got user against code: "+user.toString());
                user.setIsEnabled(true);
                userRepository.save(user);
                return "enabled";
            }
            return "not enabled";

        } catch (Exception e) {
            System.out.println("catching exception in verify user");
            return e.getMessage();
        }
    }

    private void verifyEmail(UserEntity userEntity, String email) throws MessagingException, UnsupportedEncodingException {
        if (!userEntity.getEmail().equals(email)) {
            String randomCode = RandomString.make(16);
            userEntity.setEmail(email);
            userEntity.setVerificationCode(randomCode);
            userEntity.setIsEnabled(false);
            sendVerificationEmail(userEntity, randomCode);
        }
    }

    @Override
    public String editProfile(UserEntity user) throws Exception {

            UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new Exception("User doesnt exist"));
            userEntity.setFirstname(user.getFirstname());
            userEntity.setLastname(user.getLastname());

            if(!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())){
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                userEntity.setPassword(encodedPassword);
            }
            //if email has changed then verify it
            verifyEmail(userEntity, user.getEmail());
            userEntity.setEmail(user.getEmail());
            userEntity.setDob(user.getDob());
            userEntity.setBio(user.getBio());
            userEntity.setProfileImage(user.getProfileImage());
            userRepository.save(userEntity);
            return "update successful!";

    }

    private void sendVerificationEmail(UserEntity user, String code)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "haleema.shahid@devsinc.com";
        String senderName = "Blog App";
        String subject = "Blog App Verification Code";
        String content = "Dear [[name]],<br>"
                + "Click the link below to verify your email:<br>"
                + "<h3>[[link]]</h3>"
                + "Thank you,<br>"
                + "Blog App";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname());


        String email = user.getEmail();
        String link = "http://localhost:3000/verification/" +email+"/"+ code;
        content = content.replace("[[link]]", link);

        helper.setText(content, true);

        javaMailSender.send(message);

    }
}
