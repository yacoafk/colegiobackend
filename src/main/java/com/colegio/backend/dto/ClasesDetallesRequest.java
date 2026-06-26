package com.colegio.backend.dto;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


@Data
public class ClasesDetallesRequest {
    private Integer idClase;
    private String titulo;
    private String descripcion;
    private String urlVideoconferencia;
    private LocalDateTime fechaClase;
    private LocalDateTime fechaTermino;

    private List<MaterialesSemanalesRequest> materiales;
    private List<TareasRequest> tareas;

}
