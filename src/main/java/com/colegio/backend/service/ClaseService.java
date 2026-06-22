package com.colegio.backend.service;

import com.colegio.backend.dto.ClaseRequest;
import java.util.List;

public interface ClaseService {
    List<ClaseRequest> listarTodas();
    List<ClaseRequest> listarPorCurso(Integer idCurso); // Útil para ver la agenda de un solo curso
    ClaseRequest obtenerPorId(Integer id);
    ClaseRequest registrar(ClaseRequest request);
    ClaseRequest modificar(Integer id, ClaseRequest request);
    void eliminar(Integer id);
}