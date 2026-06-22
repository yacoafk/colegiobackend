package com.colegio.backend.controller;

import com.colegio.backend.dto.AsistenciaRequest;
import com.colegio.backend.model.AsistenciaEstudiantes;
import com.colegio.backend.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAsistencia(@RequestBody AsistenciaRequest request) {
        try {
            asistenciaService.registrarOModificarAsistencia(request);
            return ResponseEntity.ok().body("{\"message\": \"Asistencia procesada correctamente.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error interno del servidor: " + e.getMessage() + "\"}");
        }
    }

    // 🆕 NUEVO ENDPOINT: Consultar historial por Clase y Fecha
    @GetMapping("/historial")
    public ResponseEntity<?> obtenerHistorial(
            @RequestParam Integer idClase,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            List<AsistenciaEstudiantes> historial = asistenciaService.obtenerHistorialAsistencia(idClase, fecha);
            if (historial.isEmpty()) {
                return ResponseEntity.noContent().build(); // HTTP 204 si no hubo clases o nadie asistió ese día
            }
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al recuperar el historial: " + e.getMessage() + "\"}");
        }
    }
}