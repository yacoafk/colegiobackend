package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "asistencia_estudiantes")
@Data
public class AsistenciaEstudiantes {

    @EmbeddedId
    private AsistenciaEstudiantesId id = new AsistenciaEstudiantesId();

    @ManyToOne
    @MapsId("idAsistencia")
    @JoinColumn(name = "id_asistencia", foreignKey = @ForeignKey(name = "fk_ae_asistencia"))
    private Asistencia asistencia;

    @ManyToOne
    @MapsId("idEstudiante")
    @JoinColumn(name = "id_estudiante", foreignKey = @ForeignKey(name = "fk_ae_estudiante"))
    private Estudiantes estudiante;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;
}