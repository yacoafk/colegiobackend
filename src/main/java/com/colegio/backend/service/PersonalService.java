package com.colegio.backend.service;

import com.colegio.backend.dto.PersonalRegistroRequest;
import com.colegio.backend.model.Personal;
import java.util.List; // 👈 No olvides el import

public interface PersonalService {
    Personal login(String dni, String contrasenia);
    Personal registrarPersonal(PersonalRegistroRequest dto);
    Personal modificarPersonal(Integer id, PersonalRegistroRequest dto);
    void eliminarLogico(Integer id);
    List<Personal> listarTodo(); // 👈 Nuevo método
}