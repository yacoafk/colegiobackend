package com.colegio.backend.dao;

import com.colegio.backend.model.Asistencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AsistenciasRepository extends JpaRepository<Asistencias, Integer> {
    // Si en Asistencia el atributo es 'private Clases idClase;'
    Optional<Asistencias> findByIdClase_IdClase(Integer idClase);
}