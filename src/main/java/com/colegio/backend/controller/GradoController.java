package com.colegio.backend.controller;

import com.colegio.backend.dto.GradoRequest;
import com.colegio.backend.service.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
@CrossOrigin(origins = "*") 
public class GradoController {

    @Autowired
    private GradoService service;

    @GetMapping
    public ResponseEntity<List<GradoRequest>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradoRequest> obtenerPorId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<GradoRequest> registrar(@RequestBody GradoRequest request) {
        return new ResponseEntity<>(service.registrar(request), HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<GradoRequest> modificar(@PathVariable("id") Integer id, @RequestBody GradoRequest request) {
        return new ResponseEntity<>(service.modificar(id, request), HttpStatus.OK);
    }

}