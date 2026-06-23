package com.colegio.backend.service;

import com.colegio.backend.dto.PadreRequest;
import com.colegio.backend.model.Padres;
import com.colegio.backend.dto.PadreEstudianteRequest;
import java.util.List;

public interface PadreService {
    void registrarPadre(PadreRequest request);
    void modificarPadre(PadreRequest request);
    List<PadreEstudianteRequest> listarPadresPorEstudiante(Integer idEstudiante);
    List<Padres> listarTodos();
    List<PadreEstudianteRequest> listarPorSedeYGrado(Integer idSede, Integer idGrado);
    void actualizarContrasenia(Integer idPadre, String nuevaContrasenia);
}