package com.colegio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Este Lombok genera un constructor con todos los campos en este orden
@NoArgsConstructor
public class PadreEstudianteRequest {
    private Integer idPadre;       // 1er parámetro
    private String tipoDocumento;  // 2do parámetro
    private String nroDocumento;   // 3er parámetro
    private String nombres;        // 4to parámetro
    private String apellidos;      // 5to parámetro
    private String parentesco;     // 6to parámetro
}