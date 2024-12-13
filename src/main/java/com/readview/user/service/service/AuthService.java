package com.readview.user.service.service;

import com.readview.user.service.dto.LoginRequestDTO;
import com.readview.user.service.dto.LoginResponseDTO;
import com.readview.user.service.dto.TokenValidationRequestDTO;
import com.readview.user.service.dto.TokenValidationResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO requestDTO);

    TokenValidationResponseDTO validateToken(TokenValidationRequestDTO tokenValidationRequestDTO);
}
