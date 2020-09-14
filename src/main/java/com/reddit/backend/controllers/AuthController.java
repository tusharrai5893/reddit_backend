package com.reddit.backend.controllers;

import com.reddit.backend.dto.RegisterRequest;
import com.reddit.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        AuthService.signup(registerRequest);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);

    }
}
