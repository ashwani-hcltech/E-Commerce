package com.E_Commerce.service;

import com.E_Commerce.dto.LoginDto;
import com.E_Commerce.dto.LoginResponseDto;
import com.E_Commerce.dto.RegistrationDto;
import com.E_Commerce.entity.UserRegistration;
import com.E_Commerce.exception.UserAlreadyExistException;
import com.E_Commerce.repository.UserRepository;
import com.E_Commerce.security.JwtUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service

public class UserAuthServiceImpl implements UserAuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserAuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {

        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String createAccount(RegistrationDto registerDto) {
        logger.info("Registering user with email: {}", registerDto.getEmail());
        userRepository.findByEmail(registerDto.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistException("Email is already registered");
        });
        UserRegistration user = toUserEntity(registerDto);
        userRepository.save(user);
        return "User registered successfully";
    }

    private UserRegistration toUserEntity(RegistrationDto request) {
        UserRegistration user = modelMapper.map(request, UserRegistration.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    @Override
    public LoginResponseDto login(LoginDto loginRequestDto) {
        logger.info("Attempting login for user: {}", loginRequestDto.getEmail());
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        // Get user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtils.generateToken(userDetails);
        UserRegistration user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found found with email: " + loginRequestDto.getEmail()));


        return null;
    }

    private LoginResponseDto buildLoginResponse(UserRegistration user, String token, String refreshToken) {
        LoginResponseDto response = new LoginResponseDto();
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setTokenType("Bearer");
        response.setJwtToken(token);
        response.setRefreshToken("");
        return response;
    }
}
