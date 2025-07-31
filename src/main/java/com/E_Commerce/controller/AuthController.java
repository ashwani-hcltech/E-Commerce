package com.E_Commerce.controller;

import com.E_Commerce.dto.ApiResponse;
import com.E_Commerce.dto.LoginDto;
import com.E_Commerce.dto.LoginResponseDto;
import com.E_Commerce.dto.RegistrationDto;
import com.E_Commerce.service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api/v1")
public class AuthController {

    @Autowired
    UserAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> createUser(@Valid @RequestBody RegistrationDto request){
        try{
            String message = authService.createAccount(request);
            return ResponseEntity.ok(new ApiResponse<>(message, true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));

        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginDto request){
        try {
            LoginResponseDto response = authService.login(request);
            return ResponseEntity.ok(new ApiResponse<>("Login Successfully", true, response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

}
