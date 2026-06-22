package com.colegio.backend.dao;

import com.colegio.backend.model.MaterialSemanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaterialSemanalRepository extends JpaRepository<MaterialSemanal, Integer> {
    List<MaterialSemanal> findByIdClase_IdClase(Integer idClase);
}