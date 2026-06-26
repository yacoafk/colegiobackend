package com.colegio.backend.controller;

import com.colegio.backend.dto.MaterialesSemanalesRequest;
import com.colegio.backend.service.MaterialesSemanalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialesSemanalesController {

    @Autowired
    private MaterialesSemanalesService materialService;

    // POST: http://localhost:8080/api/materiales/registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMaterial(@RequestBody MaterialesSemanalesRequest request) {
        try {
            MaterialesSemanalesRequest creado = materialService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // PUT: http://localhost:8080/api/materiales/modificar/5
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarMaterial(@PathVariable Integer id, @RequestBody MaterialesSemanalesRequest request) {
        try {
            MaterialesSemanalesRequest modificado = materialService.modificar(id, request);
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
            List<MaterialesSemanalesRequest> materiales = materialService.listarMaterialPorClase(idClase);
            return ResponseEntity.ok(materiales);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}