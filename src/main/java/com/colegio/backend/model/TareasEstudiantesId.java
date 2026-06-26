package com.colegio.backend.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareasEstudiantesId implements Serializable {
    private Integer idTarea;
    private Integer idEstudiante;
}