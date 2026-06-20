package com.colegio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "mensualidades", uniqueConstraints = {
    @UniqueConstraint(name = "uq_mensualidad", columnNames = {"id_estudiante", "mes", "anio"})
})
@Data
public class Mensualidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensualidad")
    private Integer idMensualidad;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false, foreignKey = @ForeignKey(name = "fk_mensualidad_estudiante"))
    private Estudiantes idEstudiante;

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "monto_pagado", precision = 10, scale = 2)
    private BigDecimal montoPagado = BigDecimal.ZERO;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;
}