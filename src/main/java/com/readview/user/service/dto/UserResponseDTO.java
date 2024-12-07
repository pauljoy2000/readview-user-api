package com.readview.user.service.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
