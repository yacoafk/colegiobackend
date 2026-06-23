package com.colegio.backend.service.impl;

import com.colegio.backend.dto.EstudianteRequest;
import com.colegio.backend.model.Estudiantes;
import com.colegio.backend.model.TiposDocumento;
import com.colegio.backend.model.Grados;
import com.colegio.backend.model.Padres;
import com.colegio.backend.model.Sede;
import com.colegio.backend.dao.EstudianteRepository;
import com.colegio.backend.service.EstudianteService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EstudianteRepository repository;

    @Override
    public List<EstudianteRequest> listarTodos() {
        return repository.findAll().stream().map(this::convertirAEntityRequest).collect(Collectors.toList());
    }

    @Override
    public List<EstudianteRequest> listarActivos() {
        return repository.findByEstado("ACTIVO").stream().map(this::convertirAEntityRequest).collect(Collectors.toList());
    }

    @Override
    public EstudianteRequest obtenerPorId(Integer id) {
        Estudiantes e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
        return convertirAEntityRequest(e);
    }

    @Override
    @Transactional
    public EstudianteRequest registrar(EstudianteRequest dto) {
        // 1. Mapear el DTO a la entidad
        Estudiantes estudiante = mappearDtoAEntity(dto);
        
        // 2. Asignar contraseña: DNI encriptado
        // Asegúrate de que dto.getNroDocumento() no sea nulo
        if (dto.getNroDocumento() != null) {
            estudiante.setContrasenia(passwordEncoder.encode(dto.getNroDocumento()));
        } else {
            throw new RuntimeException("El número de documento es obligatorio para generar la contraseña.");
        }
        
        // 3. Guardar el estudiante
        Estudiantes guardado = repository.save(estudiante);
        
        // 4. Retornar el DTO
        return convertirAEntityRequest(guardado);
    }

    @Override
    public EstudianteRequest modificar(Integer id, EstudianteRequest dto) {
        Estudiantes existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado para actualizar"));
        
        existente.setCodigoEstudiante(dto.getCodigoEstudiante());
        existente.setNroDocumento(dto.getNroDocumento());
        existente.setNombres(dto.getNombres());
        existente.setApellidos(dto.getApellidos());
        existente.setFechaNacimiento(dto.getFechaNacimiento());
        existente.setSexo(dto.getSexo());
        existente.setMontoPension(dto.getMontoPension());

        // Re-asignación de entidades relacionadas
        TiposDocumento td = new TiposDocumento(); td.setIdTipoDoc(dto.getIdTipoDoc());
        Grados g = new Grados(); g.setIdGrado(dto.getIdGrado());
        Sede s = new Sede(); s.setIdSede(dto.getIdSede());

        existente.setIdTipoDoc(td);
        existente.setIdGrado(g);
        existente.setIdSede(s);

        Estudiantes actualizado = repository.save(existente);
        return convertirAEntityRequest(actualizado);
    }

    @Override
    public void eliminarLogico(Integer id) {
        Estudiantes existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el estudiante a retirar"));
        
        existente.setEstado("RETIRADO"); // 👈 Cambia estado en lugar de ejecutar un hard delete SQL
        repository.save(existente);
    }

    @Override
    @Transactional
    public void actualizarContrasenia(Integer idEstudiante, String nuevaContrasenia) {
        Estudiantes estudiante = repository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + idEstudiante));
        
        // Encriptar y guardar la nueva contraseña
        estudiante.setContrasenia(passwordEncoder.encode(nuevaContrasenia));
        repository.save(estudiante);
    }
    // Métodos auxiliares manuales de mapeo (puedes cambiarlos por ModelMapper o MapStruct si usas librerías)
    private EstudianteRequest convertirAEntityRequest(Estudiantes e) {
        EstudianteRequest dto = new EstudianteRequest();
        dto.setIdEstudiante(e.getIdEstudiante());
        dto.setCodigoEstudiante(e.getCodigoEstudiante());
        dto.setIdTipoDoc(e.getIdTipoDoc() != null ? e.getIdTipoDoc().getIdTipoDoc() : null);
        dto.setNroDocumento(e.getNroDocumento());
        dto.setNombres(e.getNombres());
        dto.setApellidos(e.getApellidos());
        dto.setFechaNacimiento(e.getFechaNacimiento());
        dto.setSexo(e.getSexo());
        dto.setEstado(e.getEstado());
        dto.setIdGrado(e.getIdGrado() != null ? e.getIdGrado().getIdGrado() : null);
        dto.setIdSede(e.getIdSede() != null ? e.getIdSede().getIdSede() : null);
        dto.setMontoPension(e.getMontoPension());
        return dto;
    }

    private Estudiantes mappearDtoAEntity(EstudianteRequest dto) {
        Estudiantes e = new Estudiantes();
        e.setCodigoEstudiante(dto.getCodigoEstudiante());
        e.setNroDocumento(dto.getNroDocumento());
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setSexo(dto.getSexo());
        e.setMontoPension(dto.getMontoPension());
        e.setEstado(dto.getEstado());

        if(dto.getIdTipoDoc() != null){
            TiposDocumento td = new TiposDocumento(); td.setIdTipoDoc(dto.getIdTipoDoc()); e.setIdTipoDoc(td);
        }
        if(dto.getIdGrado() != null){
            Grados g = new Grados(); g.setIdGrado(dto.getIdGrado()); e.setIdGrado(g);
        }
        if(dto.getIdSede() != null){
            Sede s = new Sede(); s.setIdSede(dto.getIdSede()); e.setIdSede(s);
        }
        return e;
    }
}