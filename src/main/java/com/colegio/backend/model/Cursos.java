package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cursos")
@Data
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer idCurso;

    @ManyToOne
    @JoinColumn(name = "id_grado", nullable = false, foreignKey = @ForeignKey(name = "fk_curso_grado"))
    private Grados idGrado;

    @ManyToOne
    @JoinColumn(name = "id_personal", nullable = false, foreignKey = @ForeignKey(name = "fk_curso_profesor"))
    private Personales idPersonal;

    @Column(name = "nombre_curso", nullable = false, length = 150)
    private String nombreCurso;
}