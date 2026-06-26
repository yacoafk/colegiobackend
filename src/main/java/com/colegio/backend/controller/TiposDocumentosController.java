package com.colegio.backend.controller;

import com.colegio.backend.dto.TiposDocumentosRequest;
import com.colegio.backend.service.TiposDocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-documento")
@CrossOrigin(origins = "*") // Se maneja de forma local o global en tu SecurityConfig
public class TiposDocumentosController {

    @Autowired
    private TiposDocumentosService tipoDocumentoService;

    // GET: http://localhost:8080/api/tipos-documento
    @GetMapping
    public ResponseEntity<List<TiposDocumentosRequest>> listarTodos() {
        return ResponseEntity.ok(tipoDocumentoService.listarTodos());
    }

    // POST: http://localhost:8080/api/tipos-documento/registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody TiposDocumentosRequest request) {
        try {
            TiposDocumentosRequest creado = tipoDocumentoService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // PUT: http://localhost:8080/api/tipos-documento/modificar/1
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody TiposDocumentosRequest request) {
        try {
            TiposDocumentosRequest modificado = tipoDocumentoService.modificar(id, request);
            return ResponseEntity.ok(modificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}