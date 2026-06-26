package com.colegio.backend.dao;

import com.colegio.backend.model.Tareas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareasRepository extends JpaRepository<Tareas, Integer> {
   List<Tareas> findByIdClase_IdClase(Integer idClase); 
}  