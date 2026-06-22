package com.colegio.backend.dao;

import com.colegio.backend.model.EstudiantePadre;
import com.colegio.backend.model.EstudiantePadreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudiantePadreRepository extends JpaRepository<EstudiantePadre, EstudiantePadreId> {
    // Busca todas las relaciones vinculadas a un estudiante específico
    List<EstudiantePadre> findByIdIdEstudiante(Integer idEstudiante);
}