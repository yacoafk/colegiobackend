package com.colegio.backend.controller;

import com.colegio.backend.dto.ClasesRequest;
import com.colegio.backend.model.Estudiantes;
import com.colegio.backend.service.ClasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
@CrossOrigin(origins = "*")
public class ClasesController {

    @Autowired
    private ClasesService claseService;

    @GetMapping
    public ResponseEntity<List<ClasesRequest>> listarTodas() {
        return ResponseEntity.ok(claseService.listarTodas());
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<ClasesRequest>> listarPorCurso(@PathVariable Integer idCurso) {
        return ResponseEntity.ok(claseService.listarPorCurso(idCurso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasesRequest> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(claseService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody ClasesRequest request) {
        try {
            ClasesRequest creada = claseService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody ClasesRequest request) {
        try {
            ClasesRequest modificada = claseService.modificar(id, request);
            return ResponseEntity.ok(modificada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{idClase}/estudiantes")
    public ResponseEntity<List<Estudiantes>> obtenerEstudiantesPorClase(@PathVariable Integer idClase) {

        List<Estudiantes> estudiantes = claseService.obtenerEstudiantesPorClase(idClase);

        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(estudiantes);
    }
    
}