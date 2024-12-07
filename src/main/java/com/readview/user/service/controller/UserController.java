package com.readview.user.service.controller;

import com.readview.user.service.constants.ApiRoutes;
import com.readview.user.service.constants.Messages;
import com.readview.user.service.dto.ApiResponse;
import com.readview.user.service.dto.UserRequestDTO;
import com.readview.user.service.dto.UserResponseDTO;
import com.readview.user.service.service.UserService;
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
@RequestMapping(ApiRoutes.user)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        log.info("START - create user");
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        log.info("END - create user");
        return ResponseEntity.ok()
                .body(
                    ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(Messages.USER_CREATED)
                        .data(userResponseDTO).build()
                );
    }
}
