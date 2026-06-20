package com.colegio.backend.controller;

import com.colegio.backend.dto.EstudianteRequest;
import com.colegio.backend.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*") // Ajustar según el puerto de tu Frontend React
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @GetMapping
    public ResponseEntity<List<EstudianteRequest>> listarActivos() {
        return new ResponseEntity<>(service.listarActivos(), HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<EstudianteRequest>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteRequest> obtenerPorId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<EstudianteRequest> registrar(@RequestBody EstudianteRequest dto) {
        return new ResponseEntity<>(service.registrar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<EstudianteRequest> modificar(@PathVariable("id") Integer id, @RequestBody EstudianteRequest dto) {
        return new ResponseEntity<>(service.modificar(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable("id") Integer id) {
        service.eliminarLogico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}