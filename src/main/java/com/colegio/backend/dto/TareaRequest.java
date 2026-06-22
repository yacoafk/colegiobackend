package com.colegio.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TareaRequest {
    private Integer idTarea;
    private Integer idClase; // ID plano enviado desde el Frontend
    private String titulo;
    private String descripcion;
    private String urlArchivoAdjunto;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
}