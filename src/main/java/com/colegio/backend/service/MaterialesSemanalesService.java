package com.colegio.backend.service;

import com.colegio.backend.dto.MaterialesSemanalesRequest;
import java.util.List;

public interface MaterialesSemanalesService {
    MaterialesSemanalesRequest registrar(MaterialesSemanalesRequest request);
    MaterialesSemanalesRequest modificar(Integer id, MaterialesSemanalesRequest request);
    List<MaterialesSemanalesRequest> listarMaterialPorClase(Integer idClase);
}