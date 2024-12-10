package com.readview.user.service.controller;

import com.readview.user.service.constants.ApiRoutes;
import com.readview.user.service.constants.Messages;
import com.readview.user.service.dto.ApiResponse;
import com.readview.user.service.dto.LoginRequestDTO;
import com.readview.user.service.dto.LoginResponseDTO;
import com.readview.user.service.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiRoutes.auth)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        log.info("START - login");
        LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
        log.info("END - login");
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(Messages.LOGIN_SUCCESS)
                                .data(loginResponseDTO)
                                .build()
                );
    }
}
