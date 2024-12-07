package com.readview.user.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private int statusCode;
    private String message;
    private Object data;
}
