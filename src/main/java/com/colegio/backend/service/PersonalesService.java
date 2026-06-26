package com.colegio.backend.service;

import com.colegio.backend.dto.PersonalesRequest;
import com.colegio.backend.model.Personales;
import java.util.List; // 👈 No olvides el import

public interface PersonalesService {
    Personales registrarPersonal(PersonalesRequest dto);
    Personales modificarPersonal(Integer id, PersonalesRequest dto);
    void eliminarLogico(Integer id);
    List<Personales> listarTodo(); // 👈 Nuevo método
}