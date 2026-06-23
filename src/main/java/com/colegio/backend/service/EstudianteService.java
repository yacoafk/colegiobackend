package com.colegio.backend.service;

import com.colegio.backend.dto.EstudianteRequest;
import java.util.List;

public interface EstudianteService {
    List<EstudianteRequest> listarTodos();
    List<EstudianteRequest> listarActivos();
    EstudianteRequest obtenerPorId(Integer id);
    EstudianteRequest registrar(EstudianteRequest dto);
    EstudianteRequest modificar(Integer id, EstudianteRequest dto);
    void eliminarLogico(Integer id); // 👈 El borrado lógico pedido
    void actualizarContrasenia(Integer idEstudiante, String nuevaContrasenia);
}