package com.colegio.backend.service.impl;

import com.colegio.backend.dao.CursosRepository;
import com.colegio.backend.dao.GradosRepository; // Asegúrate de que este nombre coincida con tu repositorio de grados
import com.colegio.backend.dao.PersonalesRepository; // Asegúrate de que este nombre coincida con tu repositorio de personal
import com.colegio.backend.dto.CursosRequest;
import com.colegio.backend.dto.MaterialesSemanalesRequest;
import com.colegio.backend.dto.TareasRequest;
import com.colegio.backend.model.Clases;
import com.colegio.backend.model.Cursos;
import com.colegio.backend.model.Grados;
import com.colegio.backend.model.Personales;
import com.colegio.backend.service.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.colegio.backend.dao.ClasesRepository;
import com.colegio.backend.dao.MaterialesSemanalesRepository;
import com.colegio.backend.dao.TareasRepository;

import com.colegio.backend.dto.ClasesDetallesRequest;
import com.colegio.backend.dto.CursosContenidosRequest;

import com.colegio.backend.model.MaterialesSemanales;
import com.colegio.backend.model.Tareas;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursosServiceImpl implements CursosService {

    @Autowired
    private CursosRepository cursoRepository;

    @Autowired
    private GradosRepository gradoRepository;

    @Autowired
    private PersonalesRepository personalRepository;

    @Autowired
    private ClasesRepository clasesRepository;

    @Autowired
    private MaterialesSemanalesRepository materialesRepository;

    @Autowired
    private TareasRepository tareasRepository;


    @Override
    @Transactional(readOnly = true)
    public List<CursosRequest> listarTodos() {
        return cursoRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CursosRequest obtenerPorId(Integer id) {
        Cursos curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        return convertirADto(curso);
    }

    @Override
    @Transactional
    public CursosRequest registrar(CursosRequest request) {
        Cursos nuevoCurso = new Cursos();
        return guardarOActualizar(nuevoCurso, request);
    }

    @Override
    @Transactional
    public CursosRequest modificar(Integer id, CursosRequest request) {
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
    public Personales obtenerPersonalPorCurso(Integer idCurso) {
        return cursoRepository.findPersonalByCursoId(idCurso)
                .orElseThrow(() -> new RuntimeException("No se encontró personal para este curso."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cursos> listarCursosPorProfesor(Integer idPersonal) {
        return cursoRepository.findByIdPersonal_IdPersonal(idPersonal);
    }

    @Override
    @Transactional(readOnly = true)
    public CursosContenidosRequest obtenerContenidoCurso(Integer idCurso) {

        Cursos curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<Clases> clases = clasesRepository.findByIdCursoIdCurso(idCurso);

        List<ClasesDetallesRequest> clasesDetalle = clases.stream().map(clase -> {

            List<MaterialesSemanalesRequest> materiales =
                    materialesRepository.findByIdClase_IdClase(clase.getIdClase())
                            .stream()
                            .map(this::convertirMaterialARequest)
                            .collect(Collectors.toList());

            List<TareasRequest> tareas =
                    tareasRepository.findByIdClase_IdClase(clase.getIdClase())
                            .stream()
                            .map(this::convertirTareaARequest)
                            .collect(Collectors.toList());

            ClasesDetallesRequest detalle = new ClasesDetallesRequest();
            detalle.setIdClase(clase.getIdClase());
            detalle.setTitulo(clase.getTitulo());
            detalle.setDescripcion(clase.getDescripcion()); 
            detalle.setFechaClase(clase.getFechaClase());   
            detalle.setFechaTermino(clase.getFechaTermino());   
            detalle.setUrlVideoconferencia(clase.getUrlVideoconferencia()); 
            detalle.setMateriales(materiales);
            detalle.setTareas(tareas);

            return detalle;

        }).collect(Collectors.toList());

        CursosContenidosRequest response = new CursosContenidosRequest();
        response.setIdCurso(curso.getIdCurso());
        response.setNombreCurso(curso.getNombreCurso());
        response.setClases(clasesDetalle);

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CursosRequest> listarCursosDtoPorProfesor(Integer idPersonal) {

        List<Cursos> cursos = cursoRepository.findByIdPersonal_IdPersonal(idPersonal);

        return cursos.stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    // --- Métodos Helper de Conversión ---
    
    private CursosRequest guardarOActualizar(Cursos entidad, CursosRequest dto) {
        entidad.setNombreCurso(dto.getNombreCurso());

        Grados grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + dto.getIdGrado()));
        entidad.setIdGrado(grado);

        Personales profesor = personalRepository.findById(dto.getIdPersonal())
                .orElseThrow(() -> new RuntimeException("Personal docente no encontrado con ID: " + dto.getIdPersonal()));
        entidad.setIdPersonal(profesor);

        Cursos guardado = cursoRepository.save(entidad);
        return convertirADto(guardado);
    }

    private CursosRequest convertirADto(Cursos curso) {
        CursosRequest dto = new CursosRequest();
        dto.setIdCurso(curso.getIdCurso());
        dto.setNombreCurso(curso.getNombreCurso());
        dto.setIdGrado(curso.getIdGrado().getIdGrado());
        dto.setIdPersonal(curso.getIdPersonal().getIdPersonal());
        
        dto.setNombreGradoVisual(curso.getIdGrado().getNombreGrado() + " - " + curso.getIdGrado().getSeccion());
        dto.setNombreProfesorCompleto(curso.getIdPersonal().getNombres() + " " + curso.getIdPersonal().getApellidos());
        return dto;
    }

    private MaterialesSemanalesRequest convertirMaterialARequest(MaterialesSemanales m) {

        MaterialesSemanalesRequest dto = new MaterialesSemanalesRequest();

        dto.setIdMaterial(m.getIdMaterial());
        dto.setIdClase(m.getIdClase().getIdClase());
        dto.setTitulo(m.getTitulo());
        dto.setDescripcion(m.getDescripcion());
        dto.setUrlArchivo(m.getUrlArchivo());
        dto.setFechaPublicacion(m.getFechaPublicacion());

        return dto;
    }

    private TareasRequest convertirTareaARequest(Tareas t) {

        TareasRequest dto = new TareasRequest();

        dto.setIdTarea(t.getIdTarea());
        dto.setIdClase(t.getIdClase().getIdClase());
        dto.setTitulo(t.getTitulo());
        dto.setDescripcion(t.getDescripcion());
        dto.setUrlArchivoAdjunto(t.getUrlArchivoAdjunto());
        dto.setFechaInicio(t.getFechaInicio());
        dto.setFechaTermino(t.getFechaTermino());

        return dto;
    }
}