package com.colegio.backend.service.impl;

import com.colegio.backend.dao.EstudiantesPadresRepository;
import com.colegio.backend.dao.PadresRepository;
import com.colegio.backend.dto.PadresEstudiantesRequest;
import com.colegio.backend.dto.PadresRequest;
import com.colegio.backend.model.*;
import com.colegio.backend.service.PadresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PadresServiceImpl implements PadresService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PadresRepository padreRepository;

    @Autowired
    private EstudiantesPadresRepository estudiantePadreRepository;

    @Override
    @Transactional
    public void registrarPadre(PadresRequest request) {
        Padres padre = new Padres();

        TiposDocumentos td = new TiposDocumentos();
        td.setIdTipoDoc(request.getIdTipoDoc());
        padre.setIdTipoDoc(td);

        padre.setNroDocumento(request.getNroDocumento());
        padre.setNombres(request.getNombres());
        padre.setApellidos(request.getApellidos());

        // NUEVOS CAMPOS
        padre.setCelular(request.getCelular());
        padre.setCorreo(request.getCorreo());
        padre.setDireccion(request.getDireccion());
        padre.setObservaciones(request.getObservaciones());

        // Contraseña inicial
        padre.setContrasenia(passwordEncoder.encode(request.getNroDocumento()));

        Padres padreGuardado = padreRepository.save(padre);

        // Relación con estudiante
        if (request.getIdEstudiante() != null) {
            EstudiantesPadres relacion = new EstudiantesPadres();

            EstudiantesPadresId idCompuesto =
                new EstudiantesPadresId(padreGuardado.getIdPadre(), request.getIdEstudiante());

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
    public void modificarPadre(PadresRequest request) {

        Padres padre = padreRepository.findById(request.getIdPadre())
                .orElseThrow(() -> new RuntimeException("El registro del apoderado no existe."));

        TiposDocumentos td = new TiposDocumentos();
        td.setIdTipoDoc(request.getIdTipoDoc());
        padre.setIdTipoDoc(td);

        padre.setNroDocumento(request.getNroDocumento());
        padre.setNombres(request.getNombres());
        padre.setApellidos(request.getApellidos());

        // 🔥 NUEVOS CAMPOS
        padre.setCelular(request.getCelular());
        padre.setCorreo(request.getCorreo());
        padre.setDireccion(request.getDireccion());
        padre.setObservaciones(request.getObservaciones());

        padreRepository.save(padre);

        // Actualizar parentesco
        if (request.getIdEstudiante() != null && request.getParentesco() != null) {

            EstudiantesPadresId idCompuesto =
                new EstudiantesPadresId(padre.getIdPadre(), request.getIdEstudiante());

            estudiantePadreRepository.findById(idCompuesto).ifPresent(relacion -> {
                relacion.setParentesco(request.getParentesco().toUpperCase());
                estudiantePadreRepository.save(relacion);
            });
        }
    }

    // Dentro de PadreServiceImpl
    @Override
    public List<PadresEstudiantesRequest> listarPadresPorEstudiante(Integer idEstudiante) {

        List<EstudiantesPadres> relaciones =
            estudiantePadreRepository.findByIdIdEstudiante(idEstudiante);

        return relaciones.stream().map(rel -> {

            Padres p = rel.getPadre();
            Estudiantes e = rel.getEstudiante();

            return new PadresEstudiantesRequest(
                    p.getIdPadre(),
                    p.getIdTipoDoc().getDescripcion(),
                    p.getNroDocumento(),
                    p.getNombres(),
                    p.getApellidos(),
                    rel.getParentesco(),

                    // 👇 estudiante
                    e.getNombres(),
                    e.getApellidos(),
                    e.getNroDocumento(),

                    // 🔥 NUEVOS CAMPOS
                    p.getCelular(),
                    p.getCorreo(),
                    p.getDireccion(),
                    p.getObservaciones()
            );

        }).collect(Collectors.toList());
    }

    @Override
    public List<Padres> listarTodos() {
        return padreRepository.findAll();
    }

    @Override
    public List<PadresEstudiantesRequest> listarPorSedeYGrado(Integer idSede, Integer idGrado) {
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