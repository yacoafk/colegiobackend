package com.colegio.backend.service;

import com.colegio.backend.dto.TareasRequest;
import java.util.List;

public interface TareasService {
    TareasRequest registrar(TareasRequest request);
    TareasRequest modificar(Integer id, TareasRequest request);

    List<TareasRequest> listarTareasPorClase(Integer idClase);
}