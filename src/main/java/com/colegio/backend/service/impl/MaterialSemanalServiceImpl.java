package com.colegio.backend.service.impl;

import com.colegio.backend.dao.ClaseRepository;
import com.colegio.backend.dao.MaterialSemanalRepository;
import com.colegio.backend.dto.MaterialSemanalRequest;
import com.colegio.backend.model.Clases;
import com.colegio.backend.model.MaterialSemanal;
import com.colegio.backend.service.MaterialSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialSemanalServiceImpl implements MaterialSemanalService {

    @Autowired
    private MaterialSemanalRepository materialRepository;

    @Autowired
    private ClaseRepository claseRepository;

    @Override
    @Transactional
    public MaterialSemanalRequest registrar(MaterialSemanalRequest request) {
        Clases clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new RuntimeException("La clase con ID " + request.getIdClase() + " no existe."));

        MaterialSemanal nuevoMaterial = new MaterialSemanal();
        nuevoMaterial.setIdClase(clase);
        nuevoMaterial.setTitulo(request.getTitulo());
        nuevoMaterial.setDescripcion(request.getDescripcion());
        nuevoMaterial.setUrlArchivo(request.getUrlArchivo());
        
        // ⏰ ASIGNACIÓN AUTOMÁTICA DE LA FECHA ACTUAL:
        nuevoMaterial.setFechaPublicacion(LocalDate.now()); 

        MaterialSemanal guardado = materialRepository.save(nuevoMaterial);
        return mapearADTO(guardado);
    }

    @Override
    @Transactional
    public MaterialSemanalRequest modificar(Integer id, MaterialSemanalRequest request) {
        MaterialSemanal materialExistente = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El material con ID " + id + " no fue encontrado."));

        if (!materialExistente.getIdClase().getIdClase().equals(request.getIdClase())) {
            Clases nuevaClase = claseRepository.findById(request.getIdClase())
                    .orElseThrow(() -> new RuntimeException("La nueva clase especificada no existe."));
            materialExistente.setIdClase(nuevaClase);
        }

        materialExistente.setTitulo(request.getTitulo());
        materialExistente.setDescripcion(request.getDescripcion());
        materialExistente.setUrlArchivo(request.getUrlArchivo());
        materialExistente.setFechaPublicacion(request.getFechaPublicacion());

        MaterialSemanal actualizado = materialRepository.save(materialExistente);
        return mapearADTO(actualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialSemanalRequest> listarMaterialPorClase(Integer idClase) {
        if (!claseRepository.existsById(idClase)) {
            throw new RuntimeException("La clase con ID " + idClase + " no existe.");
        }

        return materialRepository.findByIdClase_IdClase(idClase)
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    // Convertidor utilitario interno Entidad -> DTO
    private MaterialSemanalRequest mapearADTO(MaterialSemanal material) {
        MaterialSemanalRequest request = new MaterialSemanalRequest();
        request.setIdMaterial(material.getIdMaterial());
        request.setIdClase(material.getIdClase().getIdClase());
        request.setTitulo(material.getTitulo());
        request.setDescripcion(material.getDescripcion());
        request.setUrlArchivo(material.getUrlArchivo());
        request.setFechaPublicacion(material.getFechaPublicacion());
        return request;
    }
}