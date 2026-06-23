package com.colegio.backend.service.impl;

import com.colegio.backend.dao.PersonalRepository;
import com.colegio.backend.dto.PersonalRequest;
import com.colegio.backend.model.*;
import com.colegio.backend.service.PersonalService;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonalServiceImpl implements PersonalService {

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Personal login(String dni, String contrasenia) {

        Personal personal = personalRepository.findByNroDocumento(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        // 🛑 1. BLOQUEO TEMPORAL
        if (personal.isBloqueado() && personal.getFechaBloqueo() != null) {

            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime tiempoDesbloqueo = personal.getFechaBloqueo().plusMinutes(10);

            if (ahora.isAfter(tiempoDesbloqueo)) {
                personal.setBloqueado(false);
                personal.setIntentosFallidos(0);
                personal.setFechaBloqueo(null);
                personalRepository.save(personal);
            } else {
                long segundosRestantes = Duration.between(ahora, tiempoDesbloqueo).getSeconds();
                long minutosRestantes = (segundosRestantes / 60) + 1;

                throw new RuntimeException("Cuenta bloqueada. Intente en " + minutosRestantes + " minutos.");
            }
        }

        // 🛑 2. Estado
        if ("RETIRADO".equalsIgnoreCase(personal.getEstado())) {
            throw new RuntimeException("El usuario se encuentra en estado RETIRADO.");
        }

        // 🛑 3. Password
        if (!passwordEncoder.matches(contrasenia, personal.getContrasenia())) {

            int nuevosIntentos = (personal.getIntentosFallidos() == null ? 0 : personal.getIntentosFallidos()) + 1;
            personal.setIntentosFallidos(nuevosIntentos);

            if (nuevosIntentos >= 4) {
                personal.setBloqueado(true);
                personal.setFechaBloqueo(LocalDateTime.now());
            }

            personalRepository.save(personal);

            throw new RuntimeException("Contraseña incorrecta. Intento " + nuevosIntentos + " de 4.");
        }

        // 🛑 4. Reset
        if (personal.getIntentosFallidos() > 0 || personal.isBloqueado()) {
            personal.setIntentosFallidos(0);
            personal.setBloqueado(false);
            personal.setFechaBloqueo(null);
            personalRepository.save(personal);
        }

        return personal;
    }

    @Override
    public Personal registrarPersonal(PersonalRequest dto) {
        if (personalRepository.existsByNroDocumento(dto.getNroDocumento())) {
            throw new RuntimeException("El número de documento ya se encuentra registrado.");
        }

        Personal personal = new Personal();
        personal.setNroDocumento(dto.getNroDocumento());
        personal.setNombres(dto.getNombres());
        personal.setApellidos(dto.getApellidos());
        
        // ✨ ESTADO INICIAL: Todo personal nuevo ingresa como ACTIVO
        personal.setEstado("ACTIVO");

        String claveEncriptada = passwordEncoder.encode(dto.getContrasenia());
        personal.setContrasenia(claveEncriptada);

        // Mapeo manual de proxies
        TiposDocumento tipoDoc = new TiposDocumento();
        tipoDoc.setIdTipoDoc(dto.getIdTipoDoc());
        personal.setIdTipoDoc(tipoDoc);

        Roles rol = new Roles();
        rol.setIdRol(dto.getIdRol());
        personal.setIdRol(rol);

        Sede sede = new Sede();
        sede.setIdSede(dto.getIdSede());
        personal.setIdSede(sede);

        return personalRepository.save(personal);
    }

    @Override
    public Personal modificarPersonal(Integer id, PersonalRequest dto) {
        // 1. Verificar si el personal existe
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado con el ID: " + id));

        // 2. Validar DNI si es que intentan cambiarlo por uno ya existente de otra persona
        if (!personal.getNroDocumento().equals(dto.getNroDocumento()) && 
            personalRepository.existsByNroDocumento(dto.getNroDocumento())) {
            throw new RuntimeException("El número de documento ya pertenece a otro usuario.");
        }

        // 3. Actualizar campos básicos
        personal.setNroDocumento(dto.getNroDocumento());
        personal.setNombres(dto.getNombres());
        personal.setApellidos(dto.getApellidos());

        // 4. Actualizar contraseña SOLO si enviaron una nueva en el formulario
        if (dto.getContrasenia() != null && !dto.getContrasenia().trim().isEmpty()) {
            personal.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        }

        // 5. Actualizar Llaves Foráneas
        TiposDocumento tipoDoc = new TiposDocumento();
        tipoDoc.setIdTipoDoc(dto.getIdTipoDoc());
        personal.setIdTipoDoc(tipoDoc);

        Roles rol = new Roles();
        rol.setIdRol(dto.getIdRol());
        personal.setIdRol(rol);

        Sede sede = new Sede();
        sede.setIdSede(dto.getIdSede());
        personal.setIdSede(sede);

        return personalRepository.save(personal);
    }

    @Override
    public void eliminarLogico(Integer id) {
        // 1. Buscar el registro
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado con el ID: " + id));
        
        // 2. Cambiar su estado a RETIRADO en vez de un DELETE físico
        personal.setEstado("RETIRADO");
        
        // 3. Guardar el cambio de estado
        personalRepository.save(personal);
    }

    // Agrega este método al final de tu clase PersonalServiceImpl
    @Override
    public java.util.List<Personal> listarTodo() {
        java.util.List<Personal> lista = personalRepository.findAll();
        
        // Limpiamos las contraseñas por seguridad antes de enviarlas al Front
        lista.forEach(p -> p.setContrasenia(null));
        
        return lista;
    }

}