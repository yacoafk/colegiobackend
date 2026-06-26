package com.colegio.backend.controller; // Ajusta a tu paquete real

import com.colegio.backend.dto.SedesRequest;
import com.colegio.backend.model.Sedes;
import com.colegio.backend.service.SedesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sedes")
@CrossOrigin(origins = "*") // Recuerda que tu SecurityConfig ya maneja esto de forma global, pero se deja por consistencia
public class SedesController {

    @Autowired
    private SedesService sedeService;

    // ➕ ENDPOINT PARA REGISTRAR (POST) -> /api/sedes/registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody SedesRequest registroDTO) {
        try {
            Sedes nuevaSede = sedeService.registrarSede(registroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSede);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 🔄 ENDPOINT PARA MODIFICAR (PUT) -> /api/sedes/modificar/{id}
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody SedesRequest registroDTO) {
        try {
            Sedes sedeModificada = sedeService.modificarSede(id, registroDTO);
            return ResponseEntity.ok(sedeModificada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 📋 ENDPOINT PARA LISTAR (GET) -> /api/sedes
    @GetMapping
    public ResponseEntity<List<Sedes>> listar() {
        List<Sedes> lista = sedeService.listarTodas();
        return ResponseEntity.ok(lista);
    }
}