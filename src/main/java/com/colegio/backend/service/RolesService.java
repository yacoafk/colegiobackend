package com.colegio.backend.service;

import com.colegio.backend.dto.RolesRequest;
import com.colegio.backend.model.Roles;
import java.util.List;

public interface RolesService {
    Roles registrarRol(RolesRequest request);
    List<Roles> listarTodos();
}