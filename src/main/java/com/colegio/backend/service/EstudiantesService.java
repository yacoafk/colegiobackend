package com.colegio.backend.service;

import com.colegio.backend.dto.CursosRequest;
import com.colegio.backend.dto.EstudiantesRequest;
import java.util.List;

public interface EstudiantesService {
    List<EstudiantesRequest> listarTodos();
    List<EstudiantesRequest> listarActivos();
    EstudiantesRequest obtenerPorId(Integer id);
    EstudiantesRequest registrar(EstudiantesRequest dto);
    EstudiantesRequest modificar(Integer id, EstudiantesRequest dto);
    void eliminarLogico(Integer id); // 👈 El borrado lógico pedido
    void actualizarContrasenia(Integer idEstudiante, String nuevaContrasenia);
    List<CursosRequest> obtenerCursosPorEstudiante(Integer idEstudiante);
}