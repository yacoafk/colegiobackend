package com.colegio.backend.controller;

import com.colegio.backend.dto.PersonalRegistroRequest;
import com.colegio.backend.model.Personal;
import com.colegio.backend.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal")
@CrossOrigin(origins = "*")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody PersonalRegistroRequest registroDTO) {
        try {
            Personal nuevoPersonal = personalService.registrarPersonal(registroDTO);
            nuevoPersonal.setContrasenia(null); 
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPersonal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 🔄 ENDPOINT PARA MODIFICAR (PUT)
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody PersonalRegistroRequest registroDTO) {
        try {
            Personal personalModificado = personalService.modificarPersonal(id, registroDTO);
            personalModificado.setContrasenia(null); // Seguridad
            return ResponseEntity.ok(personalModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ❌ ENDPOINT PARA ELIMINACIÓN LÓGICA (DELETE)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            personalService.eliminarLogico(id);
            return ResponseEntity.ok("El estado del personal ha sido cambiado a RETIRADO exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Agrega este endpoint dentro de tu PersonalController

    @GetMapping
    public ResponseEntity<java.util.List<Personal>> listar() {
        java.util.List<Personal> lista = personalService.listarTodo();
        return ResponseEntity.ok(lista);
    }

}