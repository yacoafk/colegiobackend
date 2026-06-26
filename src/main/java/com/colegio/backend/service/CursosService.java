package com.colegio.backend.service;

import com.colegio.backend.dto.CursosRequest;
import com.colegio.backend.dto.CursosContenidosRequest;
import com.colegio.backend.model.Cursos;
import com.colegio.backend.model.Personales;

import java.util.List;

public interface CursosService {
    List<CursosRequest> listarTodos();
    CursosRequest obtenerPorId(Integer id);
    CursosRequest registrar(CursosRequest request);
    CursosRequest modificar(Integer id, CursosRequest request);
    void eliminar(Integer id);
    Personales obtenerPersonalPorCurso(Integer idCurso);
    List<Cursos> listarCursosPorProfesor(Integer idPersonal);
    CursosContenidosRequest obtenerContenidoCurso(Integer idCurso);
}