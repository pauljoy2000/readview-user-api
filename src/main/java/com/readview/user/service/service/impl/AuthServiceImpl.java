package com.readview.user.service.service.impl;

import com.readview.user.service.constants.Messages;
import com.readview.user.service.dto.LoginRequestDTO;
import com.readview.user.service.dto.LoginResponseDTO;
import com.readview.user.service.entity.User;
import com.readview.user.service.exception.BadRequestException;
import com.readview.user.service.security.service.JwtService;
import com.readview.user.service.service.AuthService;
import com.readview.user.service.service.UserService;
import com.readview.user.service.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();

        try {
            userService.getUserByEmail(requestDTO.getEmail());
            User user = (User) authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    requestDTO.getEmail(),
                                    requestDTO.getPassword()
                            )
                    )
                    .getPrincipal();
            Map<String, Object> claims = getClaimsForGeneratingAccessToken(user);
            String accessToken = jwtService.generateToken(claims, user);
            responseDTO.setAccessToken(accessToken);
        } catch (BadRequestException | BadCredentialsException e) {
            log.error("Invalid credentials obtained in login request");
            throw new BadRequestException(Messages.INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error("Error while processing login request. {}", e.getMessage());
            throw e;
        }

        return responseDTO;
    }

    private Map<String, Object> getClaimsForGeneratingAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        String name = CommonUtils.getFullUserName(user);
        claims.put("username", name);
        return claims;
    }
}
