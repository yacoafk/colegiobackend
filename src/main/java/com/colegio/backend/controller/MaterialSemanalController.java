package com.colegio.backend.controller;

import com.colegio.backend.dto.MaterialSemanalRequest;
import com.colegio.backend.service.MaterialSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialSemanalController {

    @Autowired
    private MaterialSemanalService materialService;

    // POST: http://localhost:8080/api/materiales/registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMaterial(@RequestBody MaterialSemanalRequest request) {
        try {
            MaterialSemanalRequest creado = materialService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // PUT: http://localhost:8080/api/materiales/modificar/5
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarMaterial(@PathVariable Integer id, @RequestBody MaterialSemanalRequest request) {
        try {
            MaterialSemanalRequest modificado = materialService.modificar(id, request);
            return ResponseEntity.ok(modificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // GET: http://localhost:8080/api/materiales/clase/3
    @GetMapping("/clase/{idClase}")
    public ResponseEntity<?> listarPorClase(@PathVariable Integer idClase) {
        try {
            List<MaterialSemanalRequest> materiales = materialService.listarMaterialPorClase(idClase);
            return ResponseEntity.ok(materiales);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}