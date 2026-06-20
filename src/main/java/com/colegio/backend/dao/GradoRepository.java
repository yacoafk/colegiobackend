package com.colegio.backend.dao;

import com.colegio.backend.model.Grados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoRepository extends JpaRepository<Grados, Integer> {
    // Aquí puedes añadir métodos derivados en el futuro si requieres filtrar por sedes
}