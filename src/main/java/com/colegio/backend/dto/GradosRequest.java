package com.colegio.backend.dto;

import lombok.Data;

@Data
public class GradosRequest {
    private Integer idGrado;
    private Integer idSede; // Recibe o envía solo el ID plano de la Sede
    private String nombreGrado;
    private String seccion;
    private String nivel;
}