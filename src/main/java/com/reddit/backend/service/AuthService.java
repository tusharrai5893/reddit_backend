package com.reddit.backend.service;

import com.reddit.backend.dto.RegisterRequest;
import com.reddit.backend.exceptions.RedditCustomException;
import com.reddit.backend.models.NotificationEmail;
import com.reddit.backend.models.User;
import com.reddit.backend.models.VerificationToken;
import com.reddit.backend.repository.UserRepo;
import com.reddit.backend.repository.VTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private  final PasswordEncoder passwordEncoder;
    private  final UserRepo userRepo;
    private  final VTokenRepo vTokenRepo;
    private  final MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUserName(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepo.save(user);

        String randomToken = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Activation mail is sent",
                user.getEmail(),
                "Click the link to activate your account for User = " + user.getEmail() +" "+
                        "http://localhost:8080/api/auth/verifyAccount/" + randomToken));

    }

    private  String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setTokenString(token);
        verificationToken.setUser(user);

        vTokenRepo.save(verificationToken);

        return token;
    }

    public void mailVerifyAccount(String token) {
        Optional<VerificationToken> tokenString = vTokenRepo.findByTokenString(token);
        tokenString.orElseThrow(()-> new RedditCustomException("Invalid Token Fetched from Repo"));
        
        // get the user and make him enabled
        getUserAndEnabled(tokenString.get());
        

    }

    @Transactional
    private void getUserAndEnabled(VerificationToken tokenString) {
        try {
            String userName = tokenString.getUser().getUserName();
            User user = userRepo.findByUserName(userName)
                    .orElseThrow(()->new RedditCustomException("User not found "+ userName));
            user.setEnabled(true);
            userRepo.save(user);
        }
        catch (Exception e){
            e.printStackTrace();

        }


    }
}
