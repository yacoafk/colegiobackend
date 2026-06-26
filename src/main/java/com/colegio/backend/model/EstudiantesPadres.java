package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estudiante_padre")
@Data
public class EstudiantesPadres {

    @EmbeddedId
    private EstudiantesPadresId id = new EstudiantesPadresId();

    @ManyToOne
    @MapsId("idPadre") // Vincula con el atributo de EstudiantePadreId
    @JoinColumn(name = "id_padre", foreignKey = @ForeignKey(name = "fk_ep_padre"))
    private Padres padre;

    @ManyToOne
    @MapsId("idEstudiante") // Vincula con el atributo de EstudiantePadreId
    @JoinColumn(name = "id_estudiante", foreignKey = @ForeignKey(name = "fk_ep_estudiante"))
    private Estudiantes estudiante;

    @Column(name = "parentesco", nullable = false, length = 50)
    private String parentesco;
}