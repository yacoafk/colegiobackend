package com.colegio.backend.service;

import com.colegio.backend.dto.AsistenciasRequest;
import com.colegio.backend.model.AsistenciasEstudiantes;
import com.colegio.backend.model.Estudiantes;
import java.time.LocalDate;
import java.util.List;

public interface AsistenciasService {
    void registrarOModificarAsistencia(AsistenciasRequest request);
    List<Estudiantes> obtenerEstudiantesPorGrado(Integer idGrado);
    
    // 🆕 NUEVO: Obtener el historial de estados de una clase en una fecha específica
    List<AsistenciasEstudiantes> obtenerHistorialAsistencia(Integer idClase, LocalDate fecha);
}