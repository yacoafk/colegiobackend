package com.colegio.backend.dto;

import lombok.Data;

import java.util.List;

public class AsistenciasRequest {

    private Integer idClase;
    private List<EstudianteAsistenciaDTO> estudiantes;

    // Getters y Setters
    public Integer getIdClase() { return idClase; }
    public void setIdClase(Integer idClase) { this.idClase = idClase; }

    public List<EstudianteAsistenciaDTO> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(List<EstudianteAsistenciaDTO> estudiantes) { this.estudiantes = estudiantes; }

    // Clase interna (Inner Class DTO)
    public static class EstudianteAsistenciaDTO {
        private Integer idEstudiante;
        private String estado;

        // Getters y Setters
        public Integer getIdEstudiante() { return idEstudiante; }
        public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}