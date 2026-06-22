package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "clases")
@Data
public class Clases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clase")
    private Integer idClase;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "fk_clase_curso"))
    private Cursos idCurso;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_videoconferencia", length = 500)
    private String urlVideoconferencia;

    @Column(name = "fecha_clase", nullable = false)
    private LocalDateTime fechaClase;

    @Column(name = "fecha_termino", nullable = false)
    private LocalDateTime fechaTermino;
}