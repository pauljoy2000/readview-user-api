package com.readview.user.service.service.impl;

import com.readview.user.service.constants.Messages;
import com.readview.user.service.dto.UserRequestDTO;
import com.readview.user.service.dto.UserResponseDTO;
import com.readview.user.service.entity.User;
import com.readview.user.service.exception.BadRequestException;
import com.readview.user.service.repository.UserRepository;
import com.readview.user.service.security.service.PasswordService;
import com.readview.user.service.service.UserService;
import com.readview.user.service.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO;
        User user = new User();

        try {
            String firstName = CommonUtils.trimAndGet(userRequestDTO.getFirstName());
            String lastName = CommonUtils.trimAndGet(userRequestDTO.getLastName());
            String email = CommonUtils.trimAndGet(userRequestDTO.getEmail());
            String hashedPassword = passwordService.hashPassword(userRequestDTO.getPassword());

            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
            if(optionalUser.isPresent()) throw new BadRequestException("User with same email exists");

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(hashedPassword);
            User savedUserRecord = userRepository.save(user);
            responseDTO = toUserResponseDTO(savedUserRecord);
        } catch (BadRequestException e) {
            log.error("Invalid request. {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error while creating user - {}", e.getMessage());
            throw e;
        }

        return responseDTO;
    }

    @Override
    public UserResponseDTO getUser(String userId) {
        User user = getUserById(userId);
        return toUserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::toUserResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUserById(String userId) {
        int count = userRepository.deleteUserById(userId);
        if(count == 0) throw new BadRequestException(Messages.INVALID_USER_ID);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new BadRequestException(Messages.INVALID_EMAIL));
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(user.getId());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setEmail(user.getEmail());
        return responseDTO;
    }

    private User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BadRequestException(Messages.INVALID_USER_ID));
    }
}
