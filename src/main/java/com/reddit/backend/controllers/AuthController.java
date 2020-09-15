package com.reddit.backend.controllers;

import com.reddit.backend.dto.RegisterRequest;
import com.reddit.backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);

    }

    @GetMapping(value = "/verifyAccount/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.mailVerifyAccount(token);
        return ResponseEntity.ok("<h2>User Activated Successfully !!</h2>");
    }

}
