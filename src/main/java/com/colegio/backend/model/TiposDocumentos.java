package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipos_documento")
@Data
public class TiposDocumentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_doc")
    private Integer idTipoDoc;

    @Column(name = "abreviatura", nullable = false, unique = true, length = 10)
    private String abreviatura;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
}