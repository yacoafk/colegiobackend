package com.colegio.backend.service.impl;

import com.colegio.backend.dao.EstudiantePadreRepository;
import com.colegio.backend.dao.PadreRepository;
import com.colegio.backend.dto.PadreEstudianteRequest;
import com.colegio.backend.dto.PadreRequest;
import com.colegio.backend.model.*;
import com.colegio.backend.service.PadreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PadreServiceImpl implements PadreService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PadreRepository padreRepository;

    @Autowired
    private EstudiantePadreRepository estudiantePadreRepository;

    @Override
    @Transactional
    public void registrarPadre(PadreRequest request) {
        Padres padre = new Padres();
        TiposDocumento td = new TiposDocumento();
        td.setIdTipoDoc(request.getIdTipoDoc());
        padre.setIdTipoDoc(td);
        
        padre.setNroDocumento(request.getNroDocumento());
        padre.setNombres(request.getNombres());
        padre.setApellidos(request.getApellidos());
        
        // Asignar el número de documento como contraseña inicial y encriptar
        padre.setContrasenia(passwordEncoder.encode(request.getNroDocumento()));
        
        Padres padreGuardado = padreRepository.save(padre);

        // 2. Si se envía un estudiante en el registro, creamos el vínculo intermedio inmediato
        if (request.getIdEstudiante() != null) {
            EstudiantePadre relacion = new EstudiantePadre();
            
            // Llave compuesta
            EstudiantePadreId idCompuesto = new EstudiantePadreId(padreGuardado.getIdPadre(), request.getIdEstudiante());
            relacion.setId(idCompuesto);
            
            relacion.setPadre(padreGuardado);
            
            Estudiantes est = new Estudiantes();
            est.setIdEstudiante(request.getIdEstudiante());
            relacion.setEstudiante(est);
            
            relacion.setParentesco(request.getParentesco().toUpperCase());

            estudiantePadreRepository.save(relacion);
        }
    }

    @Override
    @Transactional
    public void modificarPadre(PadreRequest request) {
        Padres padre = padreRepository.findById(request.getIdPadre())
                .orElseThrow(() -> new RuntimeException("El registro del apoderado no existe."));

        TiposDocumento td = new TiposDocumento();
        td.setIdTipoDoc(request.getIdTipoDoc());
        padre.setIdTipoDoc(td);
        
        padre.setNroDocumento(request.getNroDocumento());
        padre.setNombres(request.getNombres());
        padre.setApellidos(request.getApellidos());

        padreRepository.save(padre);
        
        // Opcional: Si se desea actualizar el parentesco dinámicamente en la modificación
        if (request.getIdEstudiante() != null && request.getParentesco() != null) {
            EstudiantePadreId idCompuesto = new EstudiantePadreId(padre.getIdPadre(), request.getIdEstudiante());
            estudiantePadreRepository.findById(idCompuesto).ifPresent(relacion -> {
                relacion.setParentesco(request.getParentesco().toUpperCase());
                estudiantePadreRepository.save(relacion);
            });
        }
    }

    // Dentro de PadreServiceImpl
    public List<PadreEstudianteRequest> listarPadresPorEstudiante(Integer idEstudiante) {
        List<EstudiantePadre> relaciones = estudiantePadreRepository.findByIdIdEstudiante(idEstudiante);

        return relaciones.stream().map(rel -> {
            Padres p = rel.getPadre();
            Estudiantes e = rel.getEstudiante(); // ¡Aquí obtienes la entidad estudiante!
            
            return new PadreEstudianteRequest(
                    p.getIdPadre(),
                    p.getIdTipoDoc().getDescripcion(),
                    p.getNroDocumento(),
                    p.getNombres(),
                    p.getApellidos(),
                    rel.getParentesco(),
                    e.getNombres(),
                    e.getApellidos(),
                    e.getNroDocumento()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<Padres> listarTodos() {
        return padreRepository.findAll();
    }

    @Override
    public List<PadreEstudianteRequest> listarPorSedeYGrado(Integer idSede, Integer idGrado) {
        // Ya no necesitas transformar nada, el repositorio te entrega la lista lista para el JSON
        return padreRepository.findBySedeAndGrado(idSede, idGrado);
    }

    @Override
    @Transactional
    public void actualizarContrasenia(Integer idPadre, String nuevaContrasenia) {
        Padres padre = padreRepository.findById(idPadre)
            .orElseThrow(() -> new RuntimeException("Padre no encontrado"));
        
        // Encriptar la nueva contraseña
        padre.setContrasenia(passwordEncoder.encode(nuevaContrasenia));
        padreRepository.save(padre);
    }
}