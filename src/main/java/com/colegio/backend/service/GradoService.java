package com.colegio.backend.service;

import com.colegio.backend.dto.GradoRequest;
import java.util.List;

public interface GradoService {
    List<GradoRequest> listarTodos();
    GradoRequest obtenerPorId(Integer id);
    GradoRequest registrar(GradoRequest request);
    GradoRequest modificar(Integer id, GradoRequest request);
    void eliminar(Integer id);
}