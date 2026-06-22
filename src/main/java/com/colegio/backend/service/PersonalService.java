package com.colegio.backend.service;

import com.colegio.backend.dto.PersonalRequest;
import com.colegio.backend.model.Personal;
import java.util.List; // 👈 No olvides el import

public interface PersonalService {
    Personal login(String dni, String contrasenia);
    Personal registrarPersonal(PersonalRequest dto);
    Personal modificarPersonal(Integer id, PersonalRequest dto);
    void eliminarLogico(Integer id);
    List<Personal> listarTodo(); // 👈 Nuevo método
}