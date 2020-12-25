package com.reddit.backend.service;

import com.reddit.backend.dto.JwtAuthResDto;
import com.reddit.backend.dto.LoginRequest;
import com.reddit.backend.dto.RegisterRequest;
import com.reddit.backend.exceptions.RedditCustomException;
import com.reddit.backend.mailConfig.MailService;
import com.reddit.backend.models.NotificationEmail;
import com.reddit.backend.models.User;
import com.reddit.backend.models.VerificationToken;
import com.reddit.backend.repository.UserRepo;
import com.reddit.backend.repository.VTokenRepo;
import com.reddit.backend.security.JwtProviderService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final VTokenRepo vTokenRepo;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProviderService jwtProviderService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepo.save(user);

        String randomToken = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Activation mail is sent",
                user.getEmail(),
                "Click the link to activate your account for User = " + user.getEmail() + " " +
                        "http://localhost:8080/api/auth/verifyAccount/" + randomToken));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setTokenString(token);
        verificationToken.setUser(user);

        vTokenRepo.save(verificationToken);

        return token;
    }

    @Transactional
    public void mailVerifyAccount(String token) {
        Optional<VerificationToken> tokenString = vTokenRepo.findByTokenString(token);
        tokenString.orElseThrow(() -> new RedditCustomException("Invalid Token Fetched from Repo"));

        // get the user and make him enabled
        getUserAndEnabled(tokenString.get());


    }


    private void getUserAndEnabled(VerificationToken tokenString) {
        try {
            String userName = tokenString.getUser().getUsername();
            User user = userRepo.findByUsername(userName)
                    .orElseThrow(() -> new RedditCustomException("User not found " + userName));
            user.setEnabled(true);
            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public JwtAuthResDto login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String newToken = jwtProviderService.generateJWToken(authenticate);
        return new JwtAuthResDto(newToken, loginRequest.getUsername());
    }

    public User getCurrentUser() {

        // Getting the logged in user (principal) from Security context  holder
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepo.findByUsername(principal.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username - "+ principal.getUsername()));


    }
}
