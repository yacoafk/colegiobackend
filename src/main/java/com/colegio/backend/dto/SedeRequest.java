package com.colegio.backend.dto;

import lombok.Data;

@Data
public class SedeRequest {
    private String codigoSede;
    private String nombre;
    private String ubicacion;
    private String comentarios;
}