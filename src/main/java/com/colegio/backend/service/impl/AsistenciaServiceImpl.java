package com.colegio.backend.service.impl;

import com.colegio.backend.dao.AsistenciaEstudiantesRepository;
import com.colegio.backend.dao.AsistenciaRepository;
import com.colegio.backend.dao.EstudianteRepository;
import com.colegio.backend.dto.AsistenciaRequest;
import com.colegio.backend.model.*;
import com.colegio.backend.service.AsistenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AsistenciaEstudiantesRepository asistenciaEstudiantesRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    @Transactional
    public void registrarOModificarAsistencia(AsistenciaRequest request) {
        // 1. Buscar si ya existe la cabecera para evitar violaciones de clave única o duplicados
        Asistencia asistencia = asistenciaRepository.findByIdClase_IdClase(request.getIdClase())
                .orElse(null);

        if (asistencia == null) {
            Clases clase = new Clases();
            clase.setIdClase(request.getIdClase());

            asistencia = new Asistencia();
            asistencia.setIdClase(clase);
            asistencia.setFecha(LocalDate.now());
            asistencia = asistenciaRepository.save(asistencia);
        }

        // 2. Procesar el listado masivo
        for (AsistenciaRequest.EstudianteAsistenciaDTO dto : request.getEstudiantes()) {
            
            AsistenciaEstudiantesId idCompuesto = new AsistenciaEstudiantesId(
                    asistencia.getIdAsistencia(),
                    dto.getIdEstudiante()
            );

            // Usamos existsById para no saturar con los JOINs pesados que vimos en los logs de Hibernate
            boolean yaExiste = asistenciaEstudiantesRepository.existsById(idCompuesto);

            AsistenciaEstudiantes ae = new AsistenciaEstudiantes();
            ae.setId(idCompuesto);

            if (!yaExiste) {
                Estudiantes estudiante = new Estudiantes();
                estudiante.setIdEstudiante(dto.getIdEstudiante());

                ae.setAsistencia(asistencia);
                ae.setEstudiante(estudiante);
            }

            // Asignamos el estado actualizado enviado por React ('PRESENTE' o 'AUSENTE')
            ae.setEstado(dto.getEstado());
            
            // Al usar save(), JPA detectará si es un INSERT o un UPDATE automáticamente
            asistenciaEstudiantesRepository.save(ae);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaEstudiantes> obtenerHistorialAsistencia(Integer idClase, LocalDate fecha) {
        // Busca en la tabla intermedia todos los alumnos relacionados a esa clase en esa fecha
        return asistenciaEstudiantesRepository.findByAsistencia_IdClase_IdClaseAndAsistencia_Fecha(idClase, fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiantes> obtenerEstudiantesPorGrado(Integer idGrado) {
        return estudianteRepository.findByIdGrado_IdGrado(idGrado);
    }
}