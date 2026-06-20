package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "asistencia")
@Data
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Integer idAsistencia;

    @ManyToOne
    @JoinColumn(name = "id_clase", nullable = false, foreignKey = @ForeignKey(name = "fk_asistencia_clase"))
    private Clases idClase;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
}