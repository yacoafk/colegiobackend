package com.colegio.backend.service.impl;

import com.colegio.backend.dao.ClasesRepository;
import com.colegio.backend.dao.CursosRepository;
import com.colegio.backend.dto.ClasesRequest;
import com.colegio.backend.model.Clases;
import com.colegio.backend.model.Cursos;
import com.colegio.backend.service.ClasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasesServiceImpl implements ClasesService {

    @Autowired
    private ClasesRepository claseRepository;

    @Autowired
    private CursosRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClasesRequest> listarTodas() {
        return claseRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClasesRequest> listarPorCurso(Integer idCurso) {
        return claseRepository.findByIdCursoIdCurso(idCurso).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClasesRequest obtenerPorId(Integer id) {
        Clases clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
        return convertirADto(clase);
    }

    @Override
    @Transactional
    public ClasesRequest registrar(ClasesRequest request) {
        Clases nuevaClase = new Clases();
        return guardarOActualizar(nuevaClase, request);
    }

    @Override
    @Transactional
    public ClasesRequest modificar(Integer id, ClasesRequest request) {
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

    private ClasesRequest guardarOActualizar(Clases entidad, ClasesRequest dto) {
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

    private ClasesRequest convertirADto(Clases clase) {
        ClasesRequest dto = new ClasesRequest();
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