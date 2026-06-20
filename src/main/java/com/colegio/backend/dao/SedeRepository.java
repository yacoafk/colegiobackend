package com.colegio.backend.dao;

import com.colegio.backend.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {
    
    // Para validar que no se repita el código de la sede
    boolean existsByCodigoSede(String codigoSede);
    
    // Útil si necesitas validar duplicados excluyendo la sede actual al modificar
    Optional<Sede> findByCodigoSede(String codigoSede);
}