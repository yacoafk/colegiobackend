package com.colegio.backend.service.impl;

import com.colegio.backend.dao.CursoRepository;
import com.colegio.backend.dao.GradoRepository; // Asegúrate de que este nombre coincida con tu repositorio de grados
import com.colegio.backend.dao.PersonalRepository; // Asegúrate de que este nombre coincida con tu repositorio de personal
import com.colegio.backend.dto.CursoRequest;
import com.colegio.backend.model.Cursos;
import com.colegio.backend.model.Grados;
import com.colegio.backend.model.Personal;
import com.colegio.backend.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private GradoRepository gradoRepository;

    @Autowired
    private PersonalRepository personalRepository;


    @Override
    @Transactional(readOnly = true)
    public List<CursoRequest> listarTodos() {
        return cursoRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CursoRequest obtenerPorId(Integer id) {
        Cursos curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        return convertirADto(curso);
    }

    @Override
    @Transactional
    public CursoRequest registrar(CursoRequest request) {
        Cursos nuevoCurso = new Cursos();
        return guardarOActualizar(nuevoCurso, request);
    }

    @Override
    @Transactional
    public CursoRequest modificar(Integer id, CursoRequest request) {
        Cursos cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado para modificar con ID: " + id));
        return guardarOActualizar(cursoExistente, request);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Personal obtenerPersonalPorCurso(Integer idCurso) {
        return cursoRepository.findPersonalByCursoId(idCurso)
                .orElseThrow(() -> new RuntimeException("No se encontró personal para este curso."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cursos> listarCursosPorProfesor(Integer idPersonal) {
        return cursoRepository.findByIdPersonal_IdPersonal(idPersonal);
    }

    // --- Métodos Helper de Conversión ---
    
    private CursoRequest guardarOActualizar(Cursos entidad, CursoRequest dto) {
        entidad.setNombreCurso(dto.getNombreCurso());

        Grados grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + dto.getIdGrado()));
        entidad.setIdGrado(grado);

        Personal profesor = personalRepository.findById(dto.getIdPersonal())
                .orElseThrow(() -> new RuntimeException("Personal docente no encontrado con ID: " + dto.getIdPersonal()));
        entidad.setIdPersonal(profesor);

        Cursos guardado = cursoRepository.save(entidad);
        return convertirADto(guardado);
    }

    private CursoRequest convertirADto(Cursos curso) {
        CursoRequest dto = new CursoRequest();
        dto.setIdCurso(curso.getIdCurso());
        dto.setNombreCurso(curso.getNombreCurso());
        dto.setIdGrado(curso.getIdGrado().getIdGrado());
        dto.setIdPersonal(curso.getIdPersonal().getIdPersonal());
        
        dto.setNombreGradoVisual(curso.getIdGrado().getNombreGrado() + " - " + curso.getIdGrado().getSeccion());
        dto.setNombreProfesorCompleto(curso.getIdPersonal().getNombres() + " " + curso.getIdPersonal().getApellidos());
        return dto;
    }
}