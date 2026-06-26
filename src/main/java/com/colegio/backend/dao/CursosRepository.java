package com.colegio.backend.dao;

import com.colegio.backend.model.Cursos;
import com.colegio.backend.model.Personales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CursosRepository extends JpaRepository<Cursos, Integer> {
    
    // 1. Listar cursos específicos de un grado
    List<Cursos> findByIdGrado_IdGrado(Integer idGrado);

    // 2. Listar todos los cursos a cargo de un profesor (Para el botón "Ver Cursos")
    List<Cursos> findByIdPersonal_IdPersonal(Integer idPersonal);

    // 🆕 3. AGREGA ESTA CONSULTA: Obtiene el Personal asignado a un curso por su ID
    @Query("SELECT c.idPersonal FROM Cursos c WHERE c.idCurso = :idCurso")
    Optional<Personales> findPersonalByCursoId(@Param("idCurso") Integer idCurso);
}