package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "grados")
@Data
public class Grados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grado")
    private Integer idGrado;

    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false, foreignKey = @ForeignKey(name = "fk_grado_sede"))
    private Sedes idSede; // Asumiendo que tu clase anterior se llama Sede

    @Column(name = "nombre_grado", nullable = false, length = 100)
    private String nombreGrado;

    @Column(name = "seccion", nullable = false, length = 20)
    private String seccion;

    @Column(name = "nivel", nullable = false, length = 50)
    private String nivel;
}