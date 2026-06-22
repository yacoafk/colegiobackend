package com.colegio.backend.dto;

import lombok.Data;

@Data
public class CursoRequest {
    
    private Integer idCurso;
    private String nombreCurso;
    
    // IDs planos para recibir desde el Frontend
    private Integer idGrado;
    private Integer idPersonal; // ID del profesor a cargo
    
    // Propiedades adicionales opcionales para facilitar lecturas al Frontend
    private String nombreGradoVisual;
    private String nombreProfesorCompleto;
}