package com.colegio.backend.service.impl;

import com.colegio.backend.dao.TipoDocumentoRepository;
import com.colegio.backend.dto.TipoDocumentoRequest;
import com.colegio.backend.model.TiposDocumento;
import com.colegio.backend.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumentoRequest> listarTodos() {
        return tipoDocumentoRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TipoDocumentoRequest registrar(TipoDocumentoRequest request) {
        TiposDocumento nuevoTipo = new TiposDocumento();
        nuevoTipo.setAbreviatura(request.getAbreviatura());
        nuevoTipo.setDescripcion(request.getDescripcion());

        TiposDocumento guardado = tipoDocumentoRepository.save(nuevoTipo);
        return mapearADto(guardado);
    }

    @Override
    @Transactional
    public TipoDocumentoRequest modificar(Integer id, TipoDocumentoRequest request) {
        TiposDocumento tipoExistente = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de documento con ID " + id + " no existe."));

        tipoExistente.setAbreviatura(request.getAbreviatura());
        tipoExistente.setDescripcion(request.getDescripcion());

        TiposDocumento actualizado = tipoDocumentoRepository.save(tipoExistente);
        return mapearADto(actualizado);
    }

    // Convertidor Entidad -> DTO
    private TipoDocumentoRequest mapearADto(TiposDocumento entidad) {
        TipoDocumentoRequest dto = new TipoDocumentoRequest();
        dto.setIdTipoDoc(entidad.getIdTipoDoc());
        dto.setAbreviatura(entidad.getAbreviatura());
        dto.setDescripcion(entidad.getDescripcion());
        return dto;
    }
}