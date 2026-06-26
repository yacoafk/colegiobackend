package com.colegio.backend.service.impl;

import com.colegio.backend.dao.TareasRepository;
import com.colegio.backend.dao.ClasesRepository; // Asegúrate de tener este repositorio para validar la clase
import com.colegio.backend.dto.TareasRequest;
import com.colegio.backend.model.Clases;
import com.colegio.backend.model.Tareas;
import com.colegio.backend.service.TareasService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TareasServiceImpl implements TareasService {

    @Autowired
    private TareasRepository tareaRepository;

    @Autowired
    private ClasesRepository claseRepository;

    @Override
    @Transactional
    public TareasRequest registrar(TareasRequest request) {
        // 1. Validar que la clase exista en el sistema
        Clases clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new RuntimeException("La clase con ID " + request.getIdClase() + " no existe."));

        // 2. Mapear DTO -> Entidad
        Tareas nuevaTarea = new Tareas();
        nuevaTarea.setIdClase(clase);
        nuevaTarea.setTitulo(request.getTitulo());
        nuevaTarea.setDescripcion(request.getDescripcion());
        nuevaTarea.setUrlArchivoAdjunto(request.getUrlArchivoAdjunto());
        nuevaTarea.setFechaInicio(request.getFechaInicio());
        nuevaTarea.setFechaTermino(request.getFechaTermino());

        // 3. Guardar en Base de Datos y retornar DTO mapeado
        Tareas guardada = tareaRepository.save(nuevaTarea);
        return mapearADataTransferObject(guardada);
    }

    @Override
    @Transactional
    public TareasRequest modificar(Integer id, TareasRequest request) {
        // 1. Validar que la tarea a modificar exista
        Tareas tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La tarea con ID " + id + " no fue encontrada."));

        // 2. Si cambiaron la clase de la tarea, validar la nueva clase
        if (!tareaExistente.getIdClase().getIdClase().equals(request.getIdClase())) {
            Clases nuevaClase = claseRepository.findById(request.getIdClase())
                    .orElseThrow(() -> new RuntimeException("La nueva clase especificada no existe."));
            tareaExistente.setIdClase(nuevaClase);
        }

        // 3. Actualizar campos mutables
        tareaExistente.setTitulo(request.getTitulo());
        tareaExistente.setDescripcion(request.getDescripcion());
        tareaExistente.setUrlArchivoAdjunto(request.getUrlArchivoAdjunto());
        tareaExistente.setFechaInicio(request.getFechaInicio());
        tareaExistente.setFechaTermino(request.getFechaTermino());

        Tareas actualizada = tareaRepository.save(tareaExistente);
        return mapearADataTransferObject(actualizada);
    }


    @Override
    @Transactional(readOnly = true)
    public List<TareasRequest> listarTareasPorClase(Integer idClase) {
        // 1. Validar si prefieres que lance un error si la clase no existe (Opcional)
        if (!claseRepository.existsById(idClase)) {
            throw new RuntimeException("La clase con ID " + idClase + " no existe.");
        }

        // 2. Buscar, transformar a DTO y retornar la lista
        return tareaRepository.findByIdClase_IdClase(idClase)
                .stream()
                .map(this::mapearADataTransferObject)
                .collect(Collectors.toList());
    }

    // Convertidor utilitario interno Entidad -> DTO
    private TareasRequest mapearADataTransferObject(Tareas tarea) {
        TareasRequest request = new TareasRequest();
        request.setIdTarea(tarea.getIdTarea());
        request.setIdClase(tarea.getIdClase().getIdClase());
        request.setTitulo(tarea.getTitulo());
        request.setDescripcion(tarea.getDescripcion());
        request.setUrlArchivoAdjunto(tarea.getUrlArchivoAdjunto());
        request.setFechaInicio(tarea.getFechaInicio());
        request.setFechaTermino(tarea.getFechaTermino());
        return request;
    }
}