package com.reddit.backend.service;

import com.reddit.backend.dto.JwtAuthResDto;
import com.reddit.backend.dto.LoginRequestDto;
import com.reddit.backend.dto.RefreshTokenRequestDto;
import com.reddit.backend.dto.RegisterRequestDto;
import com.reddit.backend.exceptions.RedditCustomException;
import com.reddit.backend.mailConfig.MailService;
import com.reddit.backend.mailConfig.NotificationEmail;
import com.reddit.backend.models.User;
import com.reddit.backend.models.VerificationToken;
import com.reddit.backend.repository.UserRepo;
import com.reddit.backend.repository.VerificationTokenRepo;
import com.reddit.backend.security.JwtProviderService;
import com.reddit.backend.security.UserDetailsImpl;
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
    private final VerificationTokenRepo verificationTokenRepo;

    private final MailService mailService;
    private final JwtProviderService jwtProviderService;
    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;



    @Transactional
    public void signup(RegisterRequestDto registerRequestDto) {
        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepo.save(user);

        String randomToken = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Activation mail is sent, Please verify 😇😇",
                user.getEmail(),
                "Please Activate your account by clicking on link",
                "http://localhost:8080/api/auth/verifyAccount/" + randomToken,
                "Click the link to activate your account"
        ));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setTokenString(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now());

        verificationTokenRepo.save(verificationToken);

        return token;
    }

    @Transactional
    public void mailVerifyAccount(String token) {
        Optional<VerificationToken> tokenString = verificationTokenRepo.findByTokenString(token);
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

    public JwtAuthResDto login(LoginRequestDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String newToken = jwtProviderService.generateJWToken(authenticate);
        return JwtAuthResDto.builder()
                .JwtToken(newToken)
                .userName(loginRequestDto.getUsername())
                .refreshToken(refreshTokenService.generateRefreshToken().getRToken())
                .expiresAt(Instant.now().plusMillis(jwtProviderService.getJwtExpirationTimeInMillis()))
                .build();
    }

    public User getCurrentUser() {

        // Getting the logged in user (principal) from Security context  holder
        UserDetailsImpl principal =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepo.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username - " + principal.getUsername()));


    }

    public JwtAuthResDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {

        refreshTokenService.validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
        String tokenByUsername = jwtProviderService.generateRefreshJWToken(refreshTokenRequestDto.getUsername());

        return JwtAuthResDto.builder()
                .JwtToken(tokenByUsername)
                .refreshToken(refreshTokenRequestDto.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProviderService.getJwtExpirationTimeInMillis()))
                .userName(refreshTokenRequestDto.getUsername())
                .build();
    }
}
