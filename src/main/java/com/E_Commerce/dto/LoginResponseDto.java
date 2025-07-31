package com.E_Commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDto {

    private String userId;
    private String email;
    private String tokenType;
    private String JwtToken;
    private String refreshToken;

}