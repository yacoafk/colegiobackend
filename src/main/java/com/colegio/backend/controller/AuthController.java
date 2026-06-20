package com.colegio.backend.controller;

import com.colegio.backend.dto.LoginRequest;
import com.colegio.backend.model.Personal;
import com.colegio.backend.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Ajusta el puerto según use tu React (3000 o 5173)
public class AuthController {

    @Autowired
    private PersonalService personalService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Personal personalLogueado = personalService.login(loginRequest.getDni(), loginRequest.getContrasenia());
            
            // Opcional: Puedes limpiar la contraseña antes de enviarla al frontend por seguridad
            personalLogueado.setContrasenia(null); 
            
            return ResponseEntity.ok(personalLogueado);
        } catch (RuntimeException e) {
            // Retorna un error 401 (No autorizado) si el DNI o la clave fallan
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}