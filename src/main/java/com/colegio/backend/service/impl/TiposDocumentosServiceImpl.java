package com.colegio.backend.service.impl;

import com.colegio.backend.dao.TiposDocumentosRepository;
import com.colegio.backend.dto.TiposDocumentosRequest;
import com.colegio.backend.model.TiposDocumentos;
import com.colegio.backend.service.TiposDocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TiposDocumentosServiceImpl implements TiposDocumentosService {

    @Autowired
    private TiposDocumentosRepository tipoDocumentoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TiposDocumentosRequest> listarTodos() {
        return tipoDocumentoRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TiposDocumentosRequest registrar(TiposDocumentosRequest request) {
        TiposDocumentos nuevoTipo = new TiposDocumentos();
        nuevoTipo.setAbreviatura(request.getAbreviatura());
        nuevoTipo.setDescripcion(request.getDescripcion());

        TiposDocumentos guardado = tipoDocumentoRepository.save(nuevoTipo);
        return mapearADto(guardado);
    }

    @Override
    @Transactional
    public TiposDocumentosRequest modificar(Integer id, TiposDocumentosRequest request) {
        TiposDocumentos tipoExistente = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de documento con ID " + id + " no existe."));

        tipoExistente.setAbreviatura(request.getAbreviatura());
        tipoExistente.setDescripcion(request.getDescripcion());

        TiposDocumentos actualizado = tipoDocumentoRepository.save(tipoExistente);
        return mapearADto(actualizado);
    }

    // Convertidor Entidad -> DTO
    private TiposDocumentosRequest mapearADto(TiposDocumentos entidad) {
        TiposDocumentosRequest dto = new TiposDocumentosRequest();
        dto.setIdTipoDoc(entidad.getIdTipoDoc());
        dto.setAbreviatura(entidad.getAbreviatura());
        dto.setDescripcion(entidad.getDescripcion());
        return dto;
    }
}