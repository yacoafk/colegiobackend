package com.colegio.backend.service;

import com.colegio.backend.dto.TipoDocumentoRequest;
import java.util.List;

public interface TipoDocumentoService {
    List<TipoDocumentoRequest> listarTodos();
    TipoDocumentoRequest registrar(TipoDocumentoRequest request);
    TipoDocumentoRequest modificar(Integer id, TipoDocumentoRequest request);
}