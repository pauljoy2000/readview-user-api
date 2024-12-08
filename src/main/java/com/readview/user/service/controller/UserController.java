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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                                .data(userResponseDTO)
                                .build()
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(
            @PathVariable String userId
    ) {
        log.info("START - get user by ID - {}", userId);
        UserResponseDTO userResponseDTO = userService.getUser(userId);
        log.info("END - get user by ID - {}", userId);
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(Messages.USER_FETCHED)
                                .data(userResponseDTO)
                                .build()
                );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUsers() {
        log.info("START - get all users");
        List<UserResponseDTO> userResponseDTOList = userService.getAllUsers();
        log.info("END - get all users");
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(Messages.USER_FETCHED)
                                .data(userResponseDTOList)
                                .build()
                );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(
            @PathVariable String userId
    ) {
        log.info("START - delete user by ID - {}", userId);
        userService.deleteUserById(userId);
        log.info("END - delete user by ID - {}", userId);
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(Messages.USER_DELETED)
                                .build()
                );
    }
}
