package com.reddit.backend.controllers;

import com.reddit.backend.dto.JwtAuthResDto;
import com.reddit.backend.dto.LoginRequestDto;
import com.reddit.backend.dto.RefreshTokenRequestDto;
import com.reddit.backend.dto.RegisterRequestDto;
import com.reddit.backend.models.User;
import com.reddit.backend.service.AuthService;
import com.reddit.backend.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/auth")
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequestDto registerRequestDto) {
        //authService.signup(registerRequestDto);
        return authService.signup(registerRequestDto)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));

        //return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);

    }

    @GetMapping(value = "/verifyAccount/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.mailVerifyAccount(token);
        return ResponseEntity.ok("<h2>User Activated Successfully !!</h2>");
    }

    @PostMapping(value = "/login")
    public JwtAuthResDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping(value = "/refreshToken")
    public JwtAuthResDto refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return authService.refreshToken(refreshTokenRequestDto);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequestDto.getRefreshToken());
        return ResponseEntity.status(200).body("Refresh Token Deleted");
    }




}
