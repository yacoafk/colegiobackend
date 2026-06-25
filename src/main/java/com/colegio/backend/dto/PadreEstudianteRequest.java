package com.colegio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PadreEstudianteRequest {
    private Integer idPadre;
    private String tipoDocumento;
    private String nroDocumento;
    private String nombres;
    private String apellidos;
    private String parentesco;
    
    // Nuevos campos para el estudiante
    private String nombreEstudiante;
    private String apellidosEstudiante;
    private String dniEstudiante; 

    private String celular;
    private  String correo;
    private String direccion;
    private String observaciones;
}