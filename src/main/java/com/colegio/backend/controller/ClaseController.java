package com.colegio.backend.controller;

import com.colegio.backend.dto.ClaseRequest;
import com.colegio.backend.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
@CrossOrigin(origins = "*")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public ResponseEntity<List<ClaseRequest>> listarTodas() {
        return ResponseEntity.ok(claseService.listarTodas());
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<ClaseRequest>> listarPorCurso(@PathVariable Integer idCurso) {
        return ResponseEntity.ok(claseService.listarPorCurso(idCurso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseRequest> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(claseService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody ClaseRequest request) {
        try {
            ClaseRequest creada = claseService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody ClaseRequest request) {
        try {
            ClaseRequest modificada = claseService.modificar(id, request);
            return ResponseEntity.ok(modificada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}