package com.colegio.backend.dao;

import com.colegio.backend.dto.PadresEstudiantesRequest;
import com.colegio.backend.model.Padres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PadresRepository extends JpaRepository<Padres, Integer> {

       @Query("""
       SELECT new com.colegio.backend.dto.PadresEstudiantesRequest(
       p.idPadre,
       td.descripcion,
       p.nroDocumento,
       p.nombres,
       p.apellidos,
       ep.parentesco,
       e.nombres,
       e.apellidos,
       e.nroDocumento,
       p.celular,
       p.correo,
       p.direccion,
       p.observaciones
       )
       FROM EstudiantesPadres ep
       JOIN ep.padre p
       JOIN p.idTipoDoc td
       JOIN ep.estudiante e
       WHERE e.idSede.idSede = :idSede
       AND e.idGrado.idGrado = :idGrado
       """)
       List<PadresEstudiantesRequest> findBySedeAndGrado(Integer idSede, Integer idGrado);

       Optional<Padres> findByNroDocumento(String nroDocumento); 
}