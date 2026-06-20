package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarea_estudiantes")
@Data
public class TareasEstudiantes {

    @EmbeddedId
    private TareaEstudiantesId id = new TareaEstudiantesId();

    @ManyToOne
    @MapsId("idTarea")
    @JoinColumn(name = "id_tarea", foreignKey = @ForeignKey(name = "fk_te_tarea"))
    private Tareas tarea;

    @ManyToOne
    @MapsId("idEstudiante")
    @JoinColumn(name = "id_estudiante", foreignKey = @ForeignKey(name = "fk_te_estudiante"))
    private Estudiantes estudiante;

    @Column(name = "url_entrega_alumno", length = 500)
    private String urlEntregaAlumno;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "nota", precision = 4, scale = 2)
    private BigDecimal nota;
}