package com.colegio.backend.service.impl;

import com.colegio.backend.dao.PersonalesRepository;
import com.colegio.backend.dto.PersonalesRequest;
import com.colegio.backend.model.*;
import com.colegio.backend.service.PersonalesService;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonalesServiceImpl implements PersonalesService {

    @Autowired
    private PersonalesRepository personalRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Personales registrarPersonal(PersonalesRequest dto) {
        if (personalRepository.existsByNroDocumento(dto.getNroDocumento())) {
            throw new RuntimeException("El número de documento ya se encuentra registrado.");
        }

        Personales personal = new Personales();
        personal.setNroDocumento(dto.getNroDocumento());
        personal.setNombres(dto.getNombres());
        personal.setApellidos(dto.getApellidos());
        
        // ✨ ESTADO INICIAL: Todo personal nuevo ingresa como ACTIVO
        personal.setEstado("ACTIVO");

        String claveEncriptada = passwordEncoder.encode(dto.getContrasenia());
        personal.setContrasenia(claveEncriptada);

        // Mapeo manual de proxies
        TiposDocumentos tipoDoc = new TiposDocumentos();
        tipoDoc.setIdTipoDoc(dto.getIdTipoDoc());
        personal.setIdTipoDoc(tipoDoc);

        Roles rol = new Roles();
        rol.setIdRol(dto.getIdRol());
        personal.setIdRol(rol);

        Sedes sede = new Sedes();
        sede.setIdSede(dto.getIdSede());
        personal.setIdSede(sede);

        return personalRepository.save(personal);
    }

    @Override
    public Personales modificarPersonal(Integer id, PersonalesRequest dto) {
        // 1. Verificar si el personal existe
        Personales personal = personalRepository.findById(id)
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
        TiposDocumentos tipoDoc = new TiposDocumentos();
        tipoDoc.setIdTipoDoc(dto.getIdTipoDoc());
        personal.setIdTipoDoc(tipoDoc);

        Roles rol = new Roles();
        rol.setIdRol(dto.getIdRol());
        personal.setIdRol(rol);

        Sedes sede = new Sedes();
        sede.setIdSede(dto.getIdSede());
        personal.setIdSede(sede);

        return personalRepository.save(personal);
    }

    @Override
    public void eliminarLogico(Integer id) {
        // 1. Buscar el registro
        Personales personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado con el ID: " + id));
        
        // 2. Cambiar su estado a RETIRADO en vez de un DELETE físico
        personal.setEstado("RETIRADO");
        
        // 3. Guardar el cambio de estado
        personalRepository.save(personal);
    }

    // Agrega este método al final de tu clase PersonalServiceImpl
    @Override
    public java.util.List<Personales> listarTodo() {
        java.util.List<Personales> lista = personalRepository.findAll();
        
        // Limpiamos las contraseñas por seguridad antes de enviarlas al Front
        lista.forEach(p -> p.setContrasenia(null));
        
        return lista;
    }

}