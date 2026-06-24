package com.colegio.backend.dto;

public record LoginResponse(
    Integer id,
    String nombres,
    String apellidos,
    String tipoUsuario,
    String rolDetallado,
    String codigoAcceso // Agregamos este campo que faltaba
) {}