package com.colegio.backend.dto;

import lombok.Data;

@Data
public class PadreRequest {
    private Integer idPadre; // Útil para modificaciones
    private Integer idTipoDoc;
    private String nroDocumento;
    private String nombres;
    private String apellidos;
    private String contrasenia;
    
    // Datos de vinculación con el estudiante al registrar
    private Integer idEstudiante; 
    private String parentesco; // Ej: "PAPÁ", "MAMÁ", "TÍO(A)"

    // Contacto y Ubicación
    private String celular;
    private String correo;
    private String direccion;
    private String observaciones;
}