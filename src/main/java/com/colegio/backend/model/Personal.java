package com.colegio.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "personal")
@Data
public class Personal implements Autenticable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personal")
    private Integer idPersonal;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc", nullable = false, foreignKey = @ForeignKey(name = "fk_personal_tipo_doc"))
    private TiposDocumento idTipoDoc;

    @Column(name = "nro_documento", nullable = false, unique = true, length = 20)
    private String nroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @Column(name = "contrasenia", nullable = false, length = 100)
    private String contrasenia;

    @Column(name = "intentos_fallidos", columnDefinition = "int default 0")
    private Integer intentosFallidos = 0;

    @Column(name = "bloqueado", columnDefinition = "boolean default false")
    private boolean bloqueado = false;

    @Column(name = "fecha_bloqueo")
    private LocalDateTime fechaBloqueo;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false, foreignKey = @ForeignKey(name = "fk_personal_rol"))
    private Roles idRol;

    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false, foreignKey = @ForeignKey(name = "fk_personal_sede"))
    private Sede idSede; 

    @Override public String getContrasenia() { return this.contrasenia; }
    @Override public String getNombres() { return this.nombres; }
    @Override public String getApellidos() { return this.apellidos; }
}