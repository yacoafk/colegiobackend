package com.colegio.backend.service;

import com.colegio.backend.dto.AsistenciaRequest;
import com.colegio.backend.model.AsistenciaEstudiantes;
import com.colegio.backend.model.Estudiantes;
import java.time.LocalDate;
import java.util.List;

public interface AsistenciaService {
    void registrarOModificarAsistencia(AsistenciaRequest request);
    List<Estudiantes> obtenerEstudiantesPorGrado(Integer idGrado);
    
    // 🆕 NUEVO: Obtener el historial de estados de una clase en una fecha específica
    List<AsistenciaEstudiantes> obtenerHistorialAsistencia(Integer idClase, LocalDate fecha);
}