package com.colegio.backend.dao;

import com.colegio.backend.model.EstudiantesPadres;
import com.colegio.backend.model.EstudiantesPadresId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudiantesPadresRepository extends JpaRepository<EstudiantesPadres, EstudiantesPadresId> {
    // Busca todas las relaciones vinculadas a un estudiante específico
    List<EstudiantesPadres> findByIdIdEstudiante(Integer idEstudiante);
}