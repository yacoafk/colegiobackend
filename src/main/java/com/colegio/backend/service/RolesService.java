package com.colegio.backend.service;

import com.colegio.backend.dto.RolRequest;
import com.colegio.backend.model.Roles;
import java.util.List;

public interface RolesService {
    Roles registrarRol(RolRequest request);
    List<Roles> listarTodos();
}