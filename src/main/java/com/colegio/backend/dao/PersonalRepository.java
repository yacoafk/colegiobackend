package com.colegio.backend.dao; // o .repository

import com.colegio.backend.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Integer> {
    
    // Busca al usuario en pgAdmin por su número de documento
    Optional<Personal> findByNroDocumento(String nroDocumento);
    boolean existsByNroDocumento(String nroDocumento);
}