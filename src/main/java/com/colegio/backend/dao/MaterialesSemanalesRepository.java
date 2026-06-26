package com.colegio.backend.dao;

import com.colegio.backend.model.MaterialesSemanales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaterialesSemanalesRepository extends JpaRepository<MaterialesSemanales, Integer> {
    List<MaterialesSemanales> findByIdClase_IdClase(Integer idClase);
}