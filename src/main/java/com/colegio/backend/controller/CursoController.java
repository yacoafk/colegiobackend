package com.colegio.backend.controller;

import com.colegio.backend.dto.CursoRequest; // Asumiendo que usas este DTO para consistencia
import com.colegio.backend.model.Cursos;
import com.colegio.backend.model.Personal;
import com.colegio.backend.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*") // Centralizado o manejable desde tu SecurityConfig
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoRequest>> listarTodos() {
        return ResponseEntity.ok(cursoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoRequest> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(cursoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody CursoRequest request) {
        try {
            CursoRequest creado = cursoService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody CursoRequest request) {
        try {
            CursoRequest modificado = cursoService.modificar(id, request);
            return ResponseEntity.ok(modificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 1. TU ENDPOINT ORIGINAL: Obtiene el profesor de UN curso específico
    @GetMapping("/{idCurso}/personal")
    public ResponseEntity<?> obtenerDocenteAsignado(@PathVariable Integer idCurso) {
        try {
            Personal personal = cursoService.obtenerPersonalPorCurso(idCurso);
            return ResponseEntity.ok(personal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error interno al procesar el personal del curso.\"}");
        }
    }

    // 🆕 2. NUEVO ENDPOINT VINCULADO: Obtiene la lista de cursos a cargo de UN profesor
    // URL mapeada en React: api.get(`/cursos/profesor/${personal.idPersonal}`)
    @GetMapping("/profesor/{idPersonal}")
    public ResponseEntity<List<Cursos>> listarPorProfesor(@PathVariable Integer idPersonal) {
        try {
            List<Cursos> cursos = cursoService.listarCursosPorProfesor(idPersonal);
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}