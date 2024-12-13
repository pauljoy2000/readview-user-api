package com.readview.user.service.dto;

import lombok.Data;

@Data
public class TokenValidationResponseDTO {
    private String userId;
    private String name;
    private String email;
}
