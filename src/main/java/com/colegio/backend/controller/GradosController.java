package com.colegio.backend.controller;

import com.colegio.backend.dto.GradosRequest;
import com.colegio.backend.service.GradosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
@CrossOrigin(origins = "*") 
public class GradosController {

    @Autowired
    private GradosService service;

    @GetMapping
    public ResponseEntity<List<GradosRequest>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradosRequest> obtenerPorId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<GradosRequest> registrar(@RequestBody GradosRequest request) {
        return new ResponseEntity<>(service.registrar(request), HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<GradosRequest> modificar(@PathVariable("id") Integer id, @RequestBody GradosRequest request) {
        return new ResponseEntity<>(service.modificar(id, request), HttpStatus.OK);
    }

}