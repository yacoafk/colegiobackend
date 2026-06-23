package com.colegio.backend.dao;

import com.colegio.backend.dto.PadreEstudianteRequest;
import com.colegio.backend.model.Padres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PadreRepository extends JpaRepository<Padres, Integer> {

@Query("SELECT new com.colegio.backend.dto.PadreEstudianteRequest(" +
       "p.idPadre, td.descripcion, p.nroDocumento, p.nombres, p.apellidos, ep.parentesco, " +
       "e.nombres, e.apellidos, e.nroDocumento) " +
       "FROM EstudiantePadre ep " +
       "JOIN ep.padre p " +
       "JOIN p.idTipoDoc td " +
       "JOIN ep.estudiante e " +
       "WHERE e.idSede.idSede = :idSede AND e.idGrado.idGrado = :idGrado")
List<PadreEstudianteRequest> findBySedeAndGrado(@Param("idSede") Integer idSede, @Param("idGrado") Integer idGrado);
}