package com.colegio.backend.dao;

import com.colegio.backend.model.TiposDocumentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiposDocumentosRepository extends JpaRepository<TiposDocumentos, Integer> {
    // Spring Data JPA provee automáticamente save(), findById() y findAll()
}