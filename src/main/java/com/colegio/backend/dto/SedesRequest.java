package com.colegio.backend.dto;

import lombok.Data;

@Data
public class SedesRequest {
    private String codigoSede;
    private String nombre;
    private String ubicacion;
    private String comentarios;
}