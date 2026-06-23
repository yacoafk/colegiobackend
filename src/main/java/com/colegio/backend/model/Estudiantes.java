package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estudiantes")
@Data
public class Estudiantes {

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


    @PrePersist
    protected void onCreate() {
        if (this.estado == null) {
            this.estado = "ACTIVO"; // 👈 Forzar estado activo en el insert inicial
        }
    }
}