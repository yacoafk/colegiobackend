package com.colegio.backend.service;

import com.colegio.backend.dto.SedeRequest;
import com.colegio.backend.model.Sede;
import java.util.List;

public interface SedeService {
    Sede registrarSede(SedeRequest dto);
    Sede modificarSede(Integer id, SedeRequest dto);
    List<Sede> listarTodas(); // Añadido para que el Front pueda llenar tablas/selects
} 
