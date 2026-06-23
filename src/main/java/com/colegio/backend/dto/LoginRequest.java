package com.colegio.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String dni;
    private String contrasenia;
    Integer id;
    String nombres;
    String apellidos;
    String tipoUsuario; // "PERSONAL", "PADRE", "ESTUDIANTE"
    String rolDetallado; // "ADMINISTRADOR", "FINANZAS", "PROFESOR" (si es PERSONAL)
    String codigoAcceso;
}