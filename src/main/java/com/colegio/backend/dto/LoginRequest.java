package com.colegio.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String dni;
    private String contrasenia;
}