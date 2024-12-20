package com.readview.user.service.service;

import com.readview.user.service.dto.UserRequestDTO;
import com.readview.user.service.dto.UserResponseDTO;
import com.readview.user.service.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUser(String userId);

    List<UserResponseDTO> getAllUsers();

    void deleteUserById(String userId);

    User getUserByEmail(String email);
}
