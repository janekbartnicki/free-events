package com.example.backend.dto;

import lombok.Data;

@Data
public class UserLoginRequestDTO {

    private String email;

    private String password;
}
