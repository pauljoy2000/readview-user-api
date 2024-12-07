package com.readview.user.service.service.impl;

import com.readview.user.service.dto.UserRequestDTO;
import com.readview.user.service.dto.UserResponseDTO;
import com.readview.user.service.entity.User;
import com.readview.user.service.exception.BadRequestException;
import com.readview.user.service.repository.UserRepository;
//import com.readview.user.service.security.PasswordService;
import com.readview.user.service.service.UserService;
import com.readview.user.service.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordService passwordService;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        User user = new User();

        try {
            String firstName = CommonUtils.trimAndGet(userRequestDTO.getFirstName());
            String lastName = CommonUtils.trimAndGet(userRequestDTO.getLastName());
            String email = CommonUtils.trimAndGet(userRequestDTO.getEmail());
//            String hashedPassword = passwordService.hashPassword(userRequestDTO.getPassword());

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isPresent()) throw new BadRequestException("User with same email exists");

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(userRequestDTO.getPassword());
            User savedUserRecord = userRepository.save(user);
            responseDTO = toUserResponseDTO(savedUserRecord);
        } catch (BadRequestException e) {
            log.error("Invalid request. {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error while creating user - {}", e.getMessage());
        }

        return responseDTO;
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(user.getId());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setEmail(user.getEmail());
        return responseDTO;
    }
}
