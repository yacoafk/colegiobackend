package com.colegio.backend.dao;

import com.colegio.backend.model.AsistenciaEstudiantes;
import com.colegio.backend.model.AsistenciaEstudiantesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaEstudiantesRepository extends JpaRepository<AsistenciaEstudiantes, AsistenciaEstudiantesId> {
    
    // 🆕 NUEVO: Filtra los detalles navegando por la relación de la cabecera asistencia
    List<AsistenciaEstudiantes> findByAsistencia_IdClase_IdClaseAndAsistencia_Fecha(Integer idClase, LocalDate fecha);
}