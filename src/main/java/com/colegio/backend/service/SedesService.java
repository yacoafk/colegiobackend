package com.colegio.backend.service;

import com.colegio.backend.dto.SedesRequest;
import com.colegio.backend.model.Sedes;
import java.util.List;

public interface SedesService {
    Sedes registrarSede(SedesRequest dto);
    Sedes modificarSede(Integer id, SedesRequest dto);
    List<Sedes> listarTodas(); // Añadido para que el Front pueda llenar tablas/selects
} 
