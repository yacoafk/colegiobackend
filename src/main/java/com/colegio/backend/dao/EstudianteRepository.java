package com.colegio.backend.dao;

import com.colegio.backend.model.Estudiantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiantes, Integer> {
    // Busca estudiantes filtrando los que no han sido dados de baja
    List<Estudiantes> findByEstado(String estado);
}