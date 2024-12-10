package com.readview.user.service.service;

import com.readview.user.service.dto.LoginRequestDTO;
import com.readview.user.service.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
