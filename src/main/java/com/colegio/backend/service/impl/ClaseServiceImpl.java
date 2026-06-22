package com.colegio.backend.service.impl;

import com.colegio.backend.dao.ClaseRepository;
import com.colegio.backend.dao.CursoRepository;
import com.colegio.backend.dto.ClaseRequest;
import com.colegio.backend.model.Clases;
import com.colegio.backend.model.Cursos;
import com.colegio.backend.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClaseRequest> listarTodas() {
        return claseRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseRequest> listarPorCurso(Integer idCurso) {
        return claseRepository.findByIdCursoIdCurso(idCurso).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClaseRequest obtenerPorId(Integer id) {
        Clases clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
        return convertirADto(clase);
    }

    @Override
    @Transactional
    public ClaseRequest registrar(ClaseRequest request) {
        Clases nuevaClase = new Clases();
        return guardarOActualizar(nuevaClase, request);
    }

    @Override
    @Transactional
    public ClaseRequest modificar(Integer id, ClaseRequest request) {
        Clases claseExistente = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada para modificar con ID: " + id));
        return guardarOActualizar(claseExistente, request);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!claseRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Clase no encontrada con ID: " + id);
        }
        claseRepository.deleteById(id);
    }

    // --- Métodos de Utilidad (Mapeos Corregidos) ---

    private ClaseRequest guardarOActualizar(Clases entidad, ClaseRequest dto) {
        entidad.setTitulo(dto.getTitulo());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setUrlVideoconferencia(dto.getUrlVideoconferencia());
        entidad.setFechaClase(dto.getFechaClase());
        // 🆕 PASO DE DATOS HACIA ENTIDAD
        entidad.setFechaTermino(dto.getFechaTermino());

        Cursos curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new RuntimeException("Curso base no encontrado con ID: " + dto.getIdCurso()));
        entidad.setIdCurso(curso);

        Clases guardada = claseRepository.save(entidad);
        return convertirADto(guardada);
    }

    private ClaseRequest convertirADto(Clases clase) {
        ClaseRequest dto = new ClaseRequest();
        dto.setIdClase(clase.getIdClase());
        dto.setIdCurso(clase.getIdCurso().getIdCurso());
        dto.setTitulo(clase.getTitulo());
        dto.setDescripcion(clase.getDescripcion());
        dto.setUrlVideoconferencia(clase.getUrlVideoconferencia());
        dto.setFechaClase(clase.getFechaClase());
        // 🆕 PASO DE DATOS HACIA DTO
        dto.setFechaTermino(clase.getFechaTermino());
        
        // Enriquecemos la respuesta visual
        dto.setNombreCursoVisual(clase.getIdCurso().getNombreCurso());
        if (clase.getIdCurso().getIdGrado() != null) {
            dto.setNombreGradoVisual(clase.getIdCurso().getIdGrado().getNombreGrado() + " - " + clase.getIdCurso().getIdGrado().getSeccion());
        }
        return dto;
    }
}