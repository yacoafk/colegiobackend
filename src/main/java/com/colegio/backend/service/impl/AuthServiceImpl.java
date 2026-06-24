package com.colegio.backend.service.impl;

import com.colegio.backend.dao.*;
import com.colegio.backend.dto.*;
import com.colegio.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired private PersonalRepository personalRepo;
    @Autowired private PadreRepository padreRepo;
    @Autowired private EstudianteRepository estRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. Buscar en Personal
        var personalOpt = personalRepo.findByNroDocumento(request.getDni());
        if (personalOpt.isPresent()) {
            var personal = personalOpt.get(); // Extraemos la entidad
            if (passwordEncoder.matches(request.getContrasenia(), personal.getContrasenia())) {
                return new LoginResponse(personal.getIdPersonal(), personal.getNombres(), 
                                        personal.getApellidos(), "PERSONAL", 
                                        personal.getIdRol().getNombreRol(), null);
            }
        }

        // 2. Buscar en Estudiantes
        var estudianteOpt = estRepo.findByNroDocumento(request.getDni());
        if (estudianteOpt.isPresent()) {
            var estudiante = estudianteOpt.get();
            if (passwordEncoder.matches(request.getContrasenia(), estudiante.getContrasenia())) {
                return new LoginResponse(estudiante.getIdEstudiante(), estudiante.getNombres(), 
                                        estudiante.getApellidos(), "ESTUDIANTE", 
                                        "ESTUDIANTE", estudiante.getCodigoEstudiante());
            }
        }

        // 3. Buscar en Padres
        var padreOpt = padreRepo.findByNroDocumento(request.getDni());
        if (padreOpt.isPresent()) {
            var padre = padreOpt.get();
            if (passwordEncoder.matches(request.getContrasenia(), padre.getContrasenia())) {
                return new LoginResponse(padre.getIdPadre(), padre.getNombres(), 
                                        padre.getApellidos(), "PADRE", "PADRE", null);
            }
        }

        throw new RuntimeException("Credenciales incorrectas.");
    }
}