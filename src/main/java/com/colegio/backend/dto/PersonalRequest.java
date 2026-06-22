package com.colegio.backend.dto;

import lombok.Data;

@Data
public class PersonalRequest {
    private Integer idTipoDoc;
    private String nroDocumento;
    private String nombres;
    private String apellidos;
    private String contrasenia;
    private String estado;
    private Integer idRol;
    private Integer idSede;
}