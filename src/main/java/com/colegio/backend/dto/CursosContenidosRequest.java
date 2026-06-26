package com.colegio.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class CursosContenidosRequest {

    private Integer idCurso;
    private String nombreCurso;

    private List<ClasesDetallesRequest> clases;
    
}
