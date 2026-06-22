package com.colegio.backend.dto;

import lombok.Data;

@Data
public class TipoDocumentoRequest {
    private Integer idTipoDoc;
    private String abreviatura;
    private String descripcion;
}