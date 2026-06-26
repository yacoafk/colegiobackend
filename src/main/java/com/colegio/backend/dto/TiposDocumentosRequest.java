package com.colegio.backend.dto;

import lombok.Data;

@Data
public class TiposDocumentosRequest {
    private Integer idTipoDoc;
    private String abreviatura;
    private String descripcion;
}