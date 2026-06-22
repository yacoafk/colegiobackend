package com.colegio.backend.dao;

import com.colegio.backend.model.Clases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clases, Integer> {
    // Spring Boot descompone esto en: Buscar clases donde el objeto idCurso tenga un idCurso coincidente
    List<Clases> findByIdCursoIdCurso(Integer idCurso);
}