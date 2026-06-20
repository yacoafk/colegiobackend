package com.colegio.backend.service.impl; 

import com.colegio.backend.dto.SedeRequest;
import com.colegio.backend.model.Sede;
import com.colegio.backend.dao.SedeRepository;
import com.colegio.backend.service.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SedeServiceImpl implements SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    @Override
    public Sede registrarSede(SedeRequest dto) {
        // Validar si el código ya existe
        if (sedeRepository.existsByCodigoSede(dto.getCodigoSede())) {
            throw new RuntimeException("El código de sede ya se encuentra registrado.");
        }

        Sede sede = new Sede();
        sede.setCodigoSede(dto.getCodigoSede());
        sede.setNombre(dto.getNombre());
        sede.setUbicacion(dto.getUbicacion());
        sede.setComentarios(dto.getComentarios());

        return sedeRepository.save(sede);
    }

    @Override
    public Sede modificarSede(Integer id, SedeRequest dto) {
        // Verificar si la sede existe
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada con el ID: " + id));

        // Validar código duplicado con otros registros
        sedeRepository.findByCodigoSede(dto.getCodigoSede()).ifPresent(s -> {
            if (!s.getIdSede().equals(id)) {
                throw new RuntimeException("El código de sede ya pertenece a otra sede.");
            }
        });

        // Actualizar campos
        sede.setCodigoSede(dto.getCodigoSede());
        sede.setNombre(dto.getNombre());
        sede.setUbicacion(dto.getUbicacion());
        sede.setComentarios(dto.getComentarios());

        return sedeRepository.save(sede);
    }

    @Override
    public List<Sede> listarTodas() {
        return sedeRepository.findAll();
    }
}