package com.colegio.backend.controller;

import com.colegio.backend.dto.PadresEstudiantesRequest;
import com.colegio.backend.dto.PadresRequest;
import com.colegio.backend.model.Padres;
import com.colegio.backend.service.PadresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/padres")
@CrossOrigin(origins = "*") // Ajustar según tu configuración en SecurityConfig
public class PadresController {

    @Autowired
    private PadresService padreService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody PadresRequest request) {
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
    public ResponseEntity<?> modificar(@RequestBody PadresRequest request) {
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
            List<PadresEstudiantesRequest> lista = padreService.listarPadresPorEstudiante(idEstudiante);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al consultar los apoderados del estudiante."));
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos() {
        try {
            List<Padres> lista = padreService.listarTodos();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener la lista de apoderados."));
        }
    }

    @GetMapping("/filtrar")
    public ResponseEntity<?> filtrarPadres(@RequestParam Integer idSede, @RequestParam Integer idGrado) {
        try {
            List<PadresEstudiantesRequest> lista = padreService.listarPorSedeYGrado(idSede, idGrado);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al filtrar apoderados."));
        }
    }

    @PutMapping("/actualizar-contrasenia/{idPadre}")
    public ResponseEntity<?> actualizarContrasenia(@PathVariable Integer idPadre, @RequestBody Map<String, String> request) {
        try {
            String nuevaContrasenia = request.get("nuevaContrasenia");
            if (nuevaContrasenia == null || nuevaContrasenia.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "La contraseña no puede estar vacía"));
            }
            padreService.actualizarContrasenia(idPadre, nuevaContrasenia);
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}