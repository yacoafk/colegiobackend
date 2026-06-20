package com.colegio.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EstudianteRequest {
    private Integer idEstudiante;
    private String codigoEstudiante;
    private Integer idTipoDoc;     // Enviamos solo el ID numérico
    private String nroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String estado;         // Opcional en el Request de creación
    private Integer idGrado;       // Enviamos solo el ID numérico
    private Integer idSede;        // Enviamos solo el ID numérico
    private BigDecimal montoPension;
}