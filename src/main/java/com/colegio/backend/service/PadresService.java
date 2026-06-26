package com.colegio.backend.service;

import com.colegio.backend.dto.PadresRequest;
import com.colegio.backend.model.Padres;
import com.colegio.backend.dto.PadresEstudiantesRequest;
import java.util.List;

public interface PadresService {
    void registrarPadre(PadresRequest request);
    void modificarPadre(PadresRequest request);
    List<PadresEstudiantesRequest> listarPadresPorEstudiante(Integer idEstudiante);
    List<Padres> listarTodos();
    List<PadresEstudiantesRequest> listarPorSedeYGrado(Integer idSede, Integer idGrado);
    void actualizarContrasenia(Integer idPadre, String nuevaContrasenia);
}