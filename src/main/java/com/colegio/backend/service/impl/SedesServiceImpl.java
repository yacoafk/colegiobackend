package com.colegio.backend.service.impl; 

import com.colegio.backend.dto.SedesRequest;
import com.colegio.backend.model.Sedes;
import com.colegio.backend.dao.SedesRepository;
import com.colegio.backend.service.SedesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SedesServiceImpl implements SedesService {

    @Autowired
    private SedesRepository sedeRepository;

    @Override
    public Sedes registrarSede(SedesRequest dto) {
        // Validar si el código ya existe
        if (sedeRepository.existsByCodigoSede(dto.getCodigoSede())) {
            throw new RuntimeException("El código de sede ya se encuentra registrado.");
        }

        Sedes sede = new Sedes();
        sede.setCodigoSede(dto.getCodigoSede());
        sede.setNombre(dto.getNombre());
        sede.setUbicacion(dto.getUbicacion());
        sede.setComentarios(dto.getComentarios());

        return sedeRepository.save(sede);
    }

    @Override
    public Sedes modificarSede(Integer id, SedesRequest dto) {
        // Verificar si la sede existe
        Sedes sede = sedeRepository.findById(id)
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
    public List<Sedes> listarTodas() {
        return sedeRepository.findAll();
    }
}