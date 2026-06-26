package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estudiantes")
@Data
public class Estudiantes implements Autenticable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @Column(name = "codigo_estudiante", nullable = false, unique = true, length = 20)
    private String codigoEstudiante;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc", nullable = false, foreignKey = @ForeignKey(name = "fk_estudiante_tipo_doc"))
    private TiposDocumento idTipoDoc;

    @Column(name = "nro_documento", unique = true, length = 20)
    private String nroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_grado", nullable = false, foreignKey = @ForeignKey(name = "fk_estudiante_grado"))
    private Grados idGrado;

    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false, foreignKey = @ForeignKey(name = "fk_estudiante_sede"))
    private Sede idSede;

    @Column(name = "monto_pension", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPension = BigDecimal.ZERO;
    
    @Column(name = "contrasenia", nullable = false, length = 100)
    private String contrasenia;

    @Column(name = "intentos_fallidos", columnDefinition = "int default 0")
    private Integer intentosFallidos = 0;

    @Column(name = "bloqueado", columnDefinition = "boolean default false")
    private boolean bloqueado = false;

    @Column(name = "fecha_bloqueo")
    private LocalDateTime fechaBloqueo;

    // --- Nuevos campos para la Ficha de Matrícula ---

    // Datos de contacto y ubicación
    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "correo", length = 100)
    private String correo;

    @Column(name = "direccion", length = 255)
    private String direccion;

    // Datos de Procedencia
    @Column(name = "colegio_procedencia", length = 100)
    private String colegioProcedencia;

    // Datos Médicos
    @Column(name = "tipo_alumno", length = 50)
    private String tipoAlumno; // Ejemplo: "Regular"

    @Column(name = "recomendaciones_medicas", columnDefinition = "TEXT")
    private String recomendacionesMedicas;

    @Column(name = "tiene_informe_psicologico")
    private Boolean tieneInformePsicologico;

    @Column(name = "tiene_certificado_medico")
    private Boolean tieneCertificadoMedico;

    @Column(name = "historial_clinico", columnDefinition = "TEXT")
    private String historialClinico;

    // Otros
    @Column(name = "contacto_referencia", length = 50)
    private String contactoReferencia; // "Facebook", "Web", etc.

    @PrePersist
    protected void onCreate() {
        if (this.estado == null) {
            this.estado = "ACTIVO"; // 👈 Forzar estado activo en el insert inicial
        }
    }

    @Override public String getContrasenia() { return this.contrasenia; }
    @Override public String getNombres() { return this.nombres; }
    @Override public String getApellidos() { return this.apellidos; }
}