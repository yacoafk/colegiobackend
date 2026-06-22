package com.colegio.backend.service;

import com.colegio.backend.dto.TareaRequest;
import java.util.List;

public interface TareaService {
    TareaRequest registrar(TareaRequest request);
    TareaRequest modificar(Integer id, TareaRequest request);

    List<TareaRequest> listarTareasPorClase(Integer idClase);
}