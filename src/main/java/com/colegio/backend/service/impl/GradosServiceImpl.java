package com.colegio.backend.service.impl;

import com.colegio.backend.dao.GradosRepository;
import com.colegio.backend.dto.GradosRequest;
import com.colegio.backend.model.Grados;
import com.colegio.backend.model.Sedes;
import com.colegio.backend.service.GradosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradosServiceImpl implements GradosService {

    @Autowired
    private GradosRepository repository;

    @Override
    public List<GradosRequest> listarTodos() {
        return repository.findAll().stream()
                .map(this::convertirAEntityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradosRequest obtenerPorId(Integer id) {
        Grados grado = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));
        return convertirAEntityDTO(grado);
    }

    @Override
    public GradosRequest registrar(GradosRequest request) {
        Grados grado = mappearDtoAEntity(request);
        Grados guardado = repository.save(grado);
        return convertirAEntityDTO(guardado);
    }

    @Override
    public GradosRequest modificar(Integer id, GradosRequest request) {
        Grados existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado para actualizar"));

        existente.setNombreGrado(request.getNombreGrado());
        existente.setSeccion(request.getSeccion());
        existente.setNivel(request.getNivel());

        // Vinculación relacional mediante ID plano
        if (request.getIdSede() != null) {
            Sedes nuevaSede = new Sedes();
            nuevaSede.setIdSede(request.getIdSede());
            existente.setIdSede(nuevaSede);
        }

        Grados actualizado = repository.save(existente);
        return convertirAEntityDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se encontró el grado a eliminar");
        }
        repository.deleteById(id);
    }

    // --- MÉTODOS DE MAPEO AUXILIARES ---
    private GradosRequest convertirAEntityDTO(Grados g) {
        GradosRequest dto = new GradosRequest();
        dto.setIdGrado(g.getIdGrado());
        dto.setNombreGrado(g.getNombreGrado());
        dto.setSeccion(g.getSeccion());
        dto.setNivel(g.getNivel());
        dto.setIdSede(g.getIdSede() != null ? g.getIdSede().getIdSede() : null);
        return dto;
    }

    private Grados mappearDtoAEntity(GradosRequest dto) {
        Grados g = new Grados();
        g.setNombreGrado(dto.getNombreGrado());
        g.setSeccion(dto.getSeccion());
        g.setNivel(dto.getNivel());

        if (dto.getIdSede() != null) {
            Sedes s = new Sedes();
            s.setIdSede(dto.getIdSede());
            g.setIdSede(s);
        }
        return g;
    }
}