package com.E_Commerce.service;

import com.E_Commerce.dto.LoginDto;
import com.E_Commerce.dto.LoginResponseDto;
import com.E_Commerce.dto.RegistrationDto;

public interface UserAuthService {

    String createAccount(RegistrationDto registerDto);

    LoginResponseDto login(LoginDto loginRequestDto);

}