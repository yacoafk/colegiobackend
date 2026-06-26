package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sedes")
@Data // Genera getters, setters, toString, etc., automáticamente gracias a Lombok
public class Sedes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Integer idSede;

    @Column(name = "codigo_sede", nullable = false, length = 50)
    private String codigoSede;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "ubicacion", length = 255)
    private String ubicacion;

    @Column(name = "comentarios", columnDefinition = "TEXT")
    private String comentarios;
}

