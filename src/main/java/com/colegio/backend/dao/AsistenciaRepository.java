package com.colegio.backend.dao;

import com.colegio.backend.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    // Si en Asistencia el atributo es 'private Clases idClase;'
    Optional<Asistencia> findByIdClase_IdClase(Integer idClase);
}