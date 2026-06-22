package com.colegio.backend.service;

import com.colegio.backend.dto.CursoRequest;
import com.colegio.backend.model.Cursos;
import com.colegio.backend.model.Personal;

import java.util.List;

public interface CursoService {
    List<CursoRequest> listarTodos();
    CursoRequest obtenerPorId(Integer id);
    CursoRequest registrar(CursoRequest request);
    CursoRequest modificar(Integer id, CursoRequest request);
    void eliminar(Integer id);

    Personal obtenerPersonalPorCurso(Integer idCurso);

    List<Cursos> listarCursosPorProfesor(Integer idPersonal);
}