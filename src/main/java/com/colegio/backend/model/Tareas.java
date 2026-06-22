package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Data
public class Tareas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Integer idTarea;

    // 🔄 Cambiado de idCurso a idClase para cumplir la regla "cada clase tiene una tarea"
    @ManyToOne
    @JoinColumn(name = "id_clase", nullable = false, foreignKey = @ForeignKey(name = "fk_tarea_clase"))
    private Clases idClase;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_archivo_adjunto", length = 500)
    private String urlArchivoAdjunto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_termino", nullable = false)
    private LocalDateTime fechaTermino;
}