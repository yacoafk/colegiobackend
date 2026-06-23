package com.colegio.backend.controller;

import com.colegio.backend.dto.EstudianteRequest;
import com.colegio.backend.model.Estudiantes;
import com.colegio.backend.service.AsistenciaService;
import com.colegio.backend.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*") // Ajustar según el puerto de tu Frontend React
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @Autowired
    private AsistenciaService asistenciaService;

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

    @GetMapping("/grado/{idGrado}")
    public ResponseEntity<List<Estudiantes>> obtenerEstudiantesPorGrado(@PathVariable Integer idGrado) {
        List<Estudiantes> estudiantes = asistenciaService.obtenerEstudiantesPorGrado(idGrado);
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(estudiantes);
    }

@PutMapping("/actualizar-contrasenia/{id}")
    public ResponseEntity<?> actualizarContrasenia(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        try {
            String nuevaContrasenia = body.get("nuevaContrasenia");
            if (nuevaContrasenia == null || nuevaContrasenia.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "La contraseña es requerida."));
            }
            // AQUÍ ESTABA EL ERROR, DEBE SER "service" (el nombre de tu variable inyectada)
            service.actualizarContrasenia(id, nuevaContrasenia); 
            
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar la contraseña: " + e.getMessage()));
        }
    }
}