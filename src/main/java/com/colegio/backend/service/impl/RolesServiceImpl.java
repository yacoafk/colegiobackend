package com.colegio.backend.service.impl;

import com.colegio.backend.dao.RolesRepository;
import com.colegio.backend.dto.RolesRequest;
import com.colegio.backend.model.Roles;
import com.colegio.backend.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Roles registrarRol(RolesRequest request) {
        // Validation: No nulo o vacío
        if (request.getNombreRol() == null || request.getNombreRol().trim().isEmpty()) {
            throw new RuntimeException("El nombre del rol no puede estar vacío.");
        }

        String nombreFormateado = request.getNombreRol().trim().toUpperCase();

        // Validation: No duplicados en BD
        if (rolesRepository.findByNombreRol(nombreFormateado).isPresent()) {
            throw new RuntimeException("El rol '" + nombreFormateado + "' ya se encuentra registrado.");
        }

        Roles nuevoRol = new Roles();
        nuevoRol.setNombreRol(nombreFormateado);

        return rolesRepository.save(nuevoRol);
    }

    @Override
    public List<Roles> listarTodos() {
        return rolesRepository.findAll();
    }
}