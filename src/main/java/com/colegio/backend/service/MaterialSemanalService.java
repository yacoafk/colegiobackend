package com.colegio.backend.service;

import com.colegio.backend.dto.MaterialSemanalRequest;
import java.util.List;

public interface MaterialSemanalService {
    MaterialSemanalRequest registrar(MaterialSemanalRequest request);
    MaterialSemanalRequest modificar(Integer id, MaterialSemanalRequest request);
    List<MaterialSemanalRequest> listarMaterialPorClase(Integer idClase);
}