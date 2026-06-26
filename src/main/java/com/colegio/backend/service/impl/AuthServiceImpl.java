package com.colegio.backend.service.impl;

import com.colegio.backend.dao.*;
import com.colegio.backend.dto.*;
import com.colegio.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.function.Function;

import com.colegio.backend.model.Autenticable;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired private PersonalRepository personalRepo;
    @Autowired private PadreRepository padreRepo;
    @Autowired private EstudianteRepository estRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        return authenticate(personalRepo.findByNroDocumento(request.getDni()), 
                (p) -> new LoginResponse(p.getIdPersonal(), p.getNombres(), p.getApellidos(), "PERSONAL", p.getIdRol().getNombreRol(), null), request)
            .or(() -> authenticate(estRepo.findByNroDocumento(request.getDni()), 
                (e) -> new LoginResponse(e.getIdEstudiante(), e.getNombres(), e.getApellidos(), "ESTUDIANTE", "ESTUDIANTE", e.getCodigoEstudiante()), request))
            .or(() -> authenticate(padreRepo.findByNroDocumento(request.getDni()), 
                (pa) -> new LoginResponse(pa.getIdPadre(), pa.getNombres(), pa.getApellidos(), "PADRE", "PADRE", null), request))
            .orElseThrow(() -> new RuntimeException("Credenciales incorrectas."));
    }

    // El método genérico ahora usa T extends Autenticable
    private <T extends Autenticable> Optional<LoginResponse> authenticate(Optional<T> entityOpt, 
                                                                           Function<T, LoginResponse> mapper, 
                                                                           LoginRequest request) {
        return entityOpt.filter(entity -> passwordEncoder.matches(request.getContrasenia(), entity.getContrasenia()))
                        .map(mapper);
    }
}