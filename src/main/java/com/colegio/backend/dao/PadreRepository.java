package com.colegio.backend.dao;

import com.colegio.backend.model.Padres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PadreRepository extends JpaRepository<Padres, Integer> {
    Optional<Padres> findByNroDocumento(String nroDocumento);
}