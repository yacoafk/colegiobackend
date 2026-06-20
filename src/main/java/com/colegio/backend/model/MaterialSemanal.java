package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "material_semanal")
@Data
public class MaterialSemanal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Integer idMaterial;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "fk_material_curso"))
    private Cursos idCurso;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_archivo", length = 500)
    private String urlArchivo;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;
}