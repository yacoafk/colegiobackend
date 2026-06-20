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
public class EstudiantePadreId implements Serializable {
    private Integer idPadre;
    private Integer idEstudiante;
}