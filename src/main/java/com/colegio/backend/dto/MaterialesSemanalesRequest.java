package com.colegio.backend.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MaterialesSemanalesRequest {
    private Integer idMaterial;
    private Integer idClase; // Para vincularlo dinámicamente desde React
    private String titulo;
    private String descripcion;
    private String urlArchivo;
    private LocalDate fechaPublicacion;
}