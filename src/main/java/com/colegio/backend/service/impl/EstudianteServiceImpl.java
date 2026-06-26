package com.colegio.backend.service.impl;

import com.colegio.backend.dto.CursoRequest;
import com.colegio.backend.dto.EstudianteRequest;
import com.colegio.backend.model.Cursos;
import com.colegio.backend.model.Estudiantes;
import com.colegio.backend.model.TiposDocumento;
import com.colegio.backend.model.Grados;
import com.colegio.backend.model.Sede;
import com.colegio.backend.dao.CursoRepository;
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

    @Autowired
    private CursoRepository cursoRepository;

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
        // 1. Mapear el DTO a la entidad (usando el helper que incluye todos los campos nuevos)
        Estudiantes estudiante = mappearDtoAEntity(dto);
        
        // 2. Asignar contraseña: DNI encriptado
        if (dto.getNroDocumento() != null) {
            estudiante.setContrasenia(passwordEncoder.encode(dto.getNroDocumento()));
        } else {
            throw new RuntimeException("El número de documento es obligatorio para generar la contraseña.");
        }
        
        // 3. Guardar el estudiante
        System.out.println("DTO: " + dto);
        Estudiantes guardado = repository.save(estudiante);
        
        // 4. Retornar el DTO
        return convertirAEntityRequest(guardado);
        }


    @Override
    @Transactional
    public EstudianteRequest modificar(Integer id, EstudianteRequest dto) {
        // 1. Buscar el estudiante existente
        Estudiantes existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        // 2. Asignar todos los campos del DTO a la entidad existente
        // Datos básicos
        existente.setCodigoEstudiante(dto.getCodigoEstudiante());
        existente.setNroDocumento(dto.getNroDocumento());
        existente.setNombres(dto.getNombres());
        existente.setApellidos(dto.getApellidos());
        existente.setFechaNacimiento(dto.getFechaNacimiento());
        existente.setSexo(dto.getSexo());
        existente.setMontoPension(dto.getMontoPension());
        existente.setEstado(dto.getEstado());

        // NUEVOS CAMPOS (Que faltaban)
        existente.setCelular(dto.getCelular());
        existente.setCorreo(dto.getCorreo());
        existente.setDireccion(dto.getDireccion());
        existente.setColegioProcedencia(dto.getColegioProcedencia());
        existente.setTipoAlumno(dto.getTipoAlumno());
        existente.setRecomendacionesMedicas(dto.getRecomendacionesMedicas());
        existente.setHistorialClinico(dto.getHistorialClinico());
        existente.setContactoReferencia(dto.getContactoReferencia());

        // Booleanos
        existente.setTieneInformePsicologico(dto.isTieneInformePsicologico());
        existente.setTieneCertificadoMedico(dto.isTieneCertificadoMedico());

        // Relaciones
        if (dto.getIdTipoDoc() != null) {
            TiposDocumento td = new TiposDocumento(); td.setIdTipoDoc(dto.getIdTipoDoc()); existente.setIdTipoDoc(td);
        }
        if (dto.getIdGrado() != null) {
            Grados g = new Grados(); g.setIdGrado(dto.getIdGrado()); existente.setIdGrado(g);
        }
        if (dto.getIdSede() != null) {
            Sede s = new Sede(); s.setIdSede(dto.getIdSede()); existente.setIdSede(s);
        }

        // 3. Guardar
        try {
            Estudiantes actualizado = repository.save(existente);
            return convertirAEntityRequest(actualizado);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public void eliminarLogico(Integer id) {
        Estudiantes existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el estudiante a retirar"));
        
        existente.setEstado("RETIRADO"); 
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


    @Override
    public List<CursoRequest> obtenerCursosPorEstudiante(Integer idEstudiante) {

        Estudiantes estudiante = repository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        if (estudiante.getIdGrado() == null) {
            throw new RuntimeException("El estudiante no tiene grado asignado");
        }

        Integer idGrado = estudiante.getIdGrado().getIdGrado();

        List<Cursos> cursos = cursoRepository.findByIdGrado_IdGrado(idGrado);

        return cursos.stream()
                .map(this::convertirCursoARequest)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares manuales de mapeo (puedes cambiarlos por ModelMapper o MapStruct si usas librerías)
    private EstudianteRequest convertirAEntityRequest(Estudiantes e) {
        EstudianteRequest dto = new EstudianteRequest();
        // Datos básicos
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

        // Nuevos campos mapeados
        dto.setCelular(e.getCelular());
        dto.setCorreo(e.getCorreo());
        dto.setDireccion(e.getDireccion());
        dto.setColegioProcedencia(e.getColegioProcedencia());
        dto.setTipoAlumno(e.getTipoAlumno());
        dto.setRecomendacionesMedicas(e.getRecomendacionesMedicas());
        dto.setTieneInformePsicologico(Boolean.TRUE.equals(e.getTieneInformePsicologico()));
        dto.setTieneCertificadoMedico(Boolean.TRUE.equals(e.getTieneCertificadoMedico()));
        dto.setHistorialClinico(e.getHistorialClinico());
        dto.setContactoReferencia(e.getContactoReferencia());

        return dto;
    }

    private Estudiantes mappearDtoAEntity(EstudianteRequest dto) {
        Estudiantes e = new Estudiantes();
        // Datos básicos
        e.setCodigoEstudiante(dto.getCodigoEstudiante());
        e.setNroDocumento(dto.getNroDocumento());
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setSexo(dto.getSexo());
        e.setMontoPension(dto.getMontoPension());
        e.setEstado(dto.getEstado());

        // Nuevos campos
        e.setCelular(dto.getCelular());
        e.setCorreo(dto.getCorreo());
        e.setDireccion(dto.getDireccion());
        e.setColegioProcedencia(dto.getColegioProcedencia());
        e.setTipoAlumno(dto.getTipoAlumno());
        e.setRecomendacionesMedicas(dto.getRecomendacionesMedicas());
        e.setTieneInformePsicologico(dto.isTieneInformePsicologico());
        e.setTieneCertificadoMedico(dto.isTieneCertificadoMedico());
        e.setHistorialClinico(dto.getHistorialClinico());
        e.setContactoReferencia(dto.getContactoReferencia());

        // Asignación de relaciones (usando referencias simples por ID)
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

    private CursoRequest convertirCursoARequest(Cursos curso) {
        CursoRequest dto = new CursoRequest();

        dto.setIdCurso(curso.getIdCurso());
        dto.setNombreCurso(curso.getNombreCurso());

        if (curso.getIdGrado() != null) {
            dto.setIdGrado(curso.getIdGrado().getIdGrado());
        }

        if (curso.getIdPersonal() != null) {
            dto.setIdPersonal(curso.getIdPersonal().getIdPersonal());
        }

        return dto;
    }

}