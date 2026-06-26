package com.colegio.backend.service;

import com.colegio.backend.dto.TiposDocumentosRequest;
import java.util.List;

public interface TiposDocumentosService {
    List<TiposDocumentosRequest> listarTodos();
    TiposDocumentosRequest registrar(TiposDocumentosRequest request);
    TiposDocumentosRequest modificar(Integer id, TiposDocumentosRequest request);
}