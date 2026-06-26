package com.colegio.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EstudiantesRequest {
    private Integer idEstudiante;
    private String codigoEstudiante;
    private Integer idTipoDoc;     // Enviamos solo el ID numérico
    private String nroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String estado;         
    private Integer idGrado;       // Enviamos solo el ID numérico
    private Integer idSede;        // Enviamos solo el ID numérico
    private BigDecimal montoPension;

    private String contrasenia;    

    // Contacto y Ubicación
    private String celular;
    private String correo;
    private String direccion;

    // Procedencia
    private String colegioProcedencia;

    // Datos Médicos (Mapeados como String para tus Varchar)
    private String tipoAlumno;
    private String recomendacionesMedicas;
    private boolean tieneInformePsicologico;
    private boolean tieneCertificadoMedico;
    private String historialClinico;

    // Referencia
    private String contactoReferencia;

}