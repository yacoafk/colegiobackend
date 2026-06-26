package com.colegio.backend.dao; // o .repository

import com.colegio.backend.model.Personales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonalesRepository extends JpaRepository<Personales, Integer> {
    
    // Busca al usuario en pgAdmin por su número de documento
    Optional<Personales> findByNroDocumento(String nroDocumento);
    boolean existsByNroDocumento(String nroDocumento);
}