package com.colegio.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClasesRequest {
    private Integer idClase;
    private Integer idCurso; 
    private String titulo;
    private String descripcion;
    private String urlVideoconferencia;
    private LocalDateTime fechaClase;
    private LocalDateTime fechaTermino;

    // Campos enriquecidos para la UI
    private String nombreCursoVisual; 
    private String nombreGradoVisual; 
}