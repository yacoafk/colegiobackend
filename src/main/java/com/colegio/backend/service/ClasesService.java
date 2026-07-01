package com.colegio.backend.service;

import com.colegio.backend.dto.ClasesRequest;
import com.colegio.backend.model.Estudiantes;

import java.util.List;

public interface ClasesService {
    List<ClasesRequest> listarTodas();
    List<ClasesRequest> listarPorCurso(Integer idCurso); // Útil para ver la agenda de un solo curso
    ClasesRequest obtenerPorId(Integer id);
    ClasesRequest registrar(ClasesRequest request);
    ClasesRequest modificar(Integer id, ClasesRequest request);
    void eliminar(Integer id);
    List<Estudiantes> obtenerEstudiantesPorClase(Integer idClase);
}