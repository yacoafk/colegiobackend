package com.colegio.backend.dao;

import com.colegio.backend.model.AsistenciasEstudiantes;
import com.colegio.backend.model.AsistenciasEstudiantesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciasEstudiantesRepository extends JpaRepository<AsistenciasEstudiantes, AsistenciasEstudiantesId> {
    
    // 🆕 NUEVO: Filtra los detalles navegando por la relación de la cabecera asistencia
    List<AsistenciasEstudiantes> findByAsistencia_IdClase_IdClaseAndAsistencia_Fecha(Integer idClase, LocalDate fecha);
}