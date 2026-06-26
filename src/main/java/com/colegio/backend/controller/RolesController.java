package com.colegio.backend.controller;

import com.colegio.backend.dto.RolesRequest;
import com.colegio.backend.model.Roles;
import com.colegio.backend.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    // ➕ CREAR ROL (POST) -> /api/roles
    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody RolesRequest request) {
        try {
            Roles nuevoRol = rolesService.registrarRol(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 📋 LISTAR ROLES (GET) -> /api/roles
    @GetMapping
    public ResponseEntity<List<Roles>> listarRoles() {
        List<Roles> lista = rolesService.listarTodos();
        return ResponseEntity.ok(lista);
    }
}