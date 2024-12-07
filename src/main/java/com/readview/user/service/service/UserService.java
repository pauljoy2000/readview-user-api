package com.readview.user.service.service;

import com.readview.user.service.dto.UserRequestDTO;
import com.readview.user.service.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
}
