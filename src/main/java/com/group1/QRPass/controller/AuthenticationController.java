package com.group1.QRPass.controller;

import com.group1.QRPass.dto.request.CreateUserRequest;
import com.group1.QRPass.dto.request.LoginRequest;
import com.group1.QRPass.dto.response.LoginResponse;
import com.group1.QRPass.dto.response.UserRegisteredResponse;
import com.group1.QRPass.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserRegisteredResponse> registerUser(@RequestBody CreateUserRequest createUserRequest){
        return ResponseEntity.ok(authenticationService.registerUser(createUserRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.loginUser(loginRequest.username(), loginRequest.password()));
    }
}
