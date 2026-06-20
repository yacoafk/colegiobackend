package com.colegio.backend.service.impl;

import com.colegio.backend.dao.GradoRepository;
import com.colegio.backend.dto.GradoRequest;
import com.colegio.backend.model.Grados;
import com.colegio.backend.model.Sede;
import com.colegio.backend.service.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradoServiceImpl implements GradoService {

    @Autowired
    private GradoRepository repository;

    @Override
    public List<GradoRequest> listarTodos() {
        return repository.findAll().stream()
                .map(this::convertirAEntityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradoRequest obtenerPorId(Integer id) {
        Grados grado = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));
        return convertirAEntityDTO(grado);
    }

    @Override
    public GradoRequest registrar(GradoRequest request) {
        Grados grado = mappearDtoAEntity(request);
        Grados guardado = repository.save(grado);
        return convertirAEntityDTO(guardado);
    }

    @Override
    public GradoRequest modificar(Integer id, GradoRequest request) {
        Grados existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado para actualizar"));

        existente.setNombreGrado(request.getNombreGrado());
        existente.setSeccion(request.getSeccion());
        existente.setNivel(request.getNivel());

        // Vinculación relacional mediante ID plano
        if (request.getIdSede() != null) {
            Sede nuevaSede = new Sede();
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
    private GradoRequest convertirAEntityDTO(Grados g) {
        GradoRequest dto = new GradoRequest();
        dto.setIdGrado(g.getIdGrado());
        dto.setNombreGrado(g.getNombreGrado());
        dto.setSeccion(g.getSeccion());
        dto.setNivel(g.getNivel());
        dto.setIdSede(g.getIdSede() != null ? g.getIdSede().getIdSede() : null);
        return dto;
    }

    private Grados mappearDtoAEntity(GradoRequest dto) {
        Grados g = new Grados();
        g.setNombreGrado(dto.getNombreGrado());
        g.setSeccion(dto.getSeccion());
        g.setNivel(dto.getNivel());

        if (dto.getIdSede() != null) {
            Sede s = new Sede();
            s.setIdSede(dto.getIdSede());
            g.setIdSede(s);
        }
        return g;
    }
}