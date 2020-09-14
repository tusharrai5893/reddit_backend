package com.reddit.backend.service;

import com.reddit.backend.dto.RegisterRequest;
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
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private static final PasswordEncoder passwordEncoder = null;
    private static final UserRepo userRepo = null;
    private static final VTokenRepo vTokenRepo = null;
    private static final MailService mailService = null;

    @Transactional
    public static void signup(RegisterRequest registerRequest) {
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
                "Click the link to activate your account =>" +
                        "http://localhost/8080/api/auth/verifyAccount" + randomToken));

    }

    private static String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setTokenString(token);
        verificationToken.setUser(user);

        vTokenRepo.save(verificationToken);

        return token;
    }
}
