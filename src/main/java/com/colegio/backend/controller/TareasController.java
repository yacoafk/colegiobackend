package com.colegio.backend.controller;

import com.colegio.backend.dto.TareasRequest;
import com.colegio.backend.service.TareasService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareasController {

    @Autowired
    private TareasService tareaService;

    // POST: http://localhost:8080/api/tareas/registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarTarea(@RequestBody TareasRequest request) {
        try {
            TareasRequest creada = tareaService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // PUT: http://localhost:8080/api/tareas/modificar/5
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarTarea(@PathVariable Integer id, @RequestBody TareasRequest request) {
        try {
            TareasRequest modificada = tareaService.modificar(id, request);
            return ResponseEntity.ok(modificada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/clase/{idClase}")
    public ResponseEntity<?> listarPorClase(@PathVariable Integer idClase) {
        try {
            List<TareasRequest> tareas = tareaService.listarTareasPorClase(idClase);
            return ResponseEntity.ok(tareas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}