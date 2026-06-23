package com.colegio.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "padres")
@Data
public class Padres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_padre")
    private Integer idPadre;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc", nullable = false, foreignKey = @ForeignKey(name = "fk_padre_tipo_doc"))
    private TiposDocumento idTipoDoc;

    @Column(name = "nro_documento", nullable = false, unique = true, length = 20)
    private String nroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "contrasenia", nullable = false, length = 100)
    private String contrasenia;

    @Column(name = "intentos_fallidos", columnDefinition = "int default 0")
    private Integer intentosFallidos = 0;

    @Column(name = "bloqueado", columnDefinition = "boolean default false")
    private boolean bloqueado = false;

    @Column(name = "fecha_bloqueo")
    private LocalDateTime fechaBloqueo;
}