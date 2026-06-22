package com.colegio.backend.dao;

import com.colegio.backend.model.TiposDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TiposDocumento, Integer> {
    // Spring Data JPA provee automáticamente save(), findById() y findAll()
}