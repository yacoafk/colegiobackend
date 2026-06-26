package com.colegio.backend.service;

import com.colegio.backend.dto.GradosRequest;
import java.util.List;

public interface GradosService {
    List<GradosRequest> listarTodos();
    GradosRequest obtenerPorId(Integer id);
    GradosRequest registrar(GradosRequest request);
    GradosRequest modificar(Integer id, GradosRequest request);
    void eliminar(Integer id);
}