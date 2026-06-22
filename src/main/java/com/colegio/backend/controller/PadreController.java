package com.colegio.backend.controller;

import com.colegio.backend.dto.PadreEstudianteRequest;
import com.colegio.backend.dto.PadreRequest;
import com.colegio.backend.service.PadreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/padres")
@CrossOrigin(origins = "*") // Ajustar según tu configuración en SecurityConfig
public class PadreController {

    @Autowired
    private PadreService padreService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody PadreRequest request) {
        try {
            padreService.registrarPadre(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Apoderado registrado y vinculado con éxito."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody PadreRequest request) {
        try {
            padreService.modificarPadre(request);
            return ResponseEntity.ok(Map.of("mensaje", "Datos del apoderado actualizados con éxito."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<?> obtenerPadresPorEstudiante(@PathVariable Integer idEstudiante) {
        try {
            List<PadreEstudianteRequest> lista = padreService.listarPadresPorEstudiante(idEstudiante);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al consultar los apoderados del estudiante."));
        }
    }
}