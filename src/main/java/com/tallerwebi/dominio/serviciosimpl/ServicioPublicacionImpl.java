package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Publicacion;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.PublicacionDTO;
import com.tallerwebi.infraestructura.RepositorioComunidad;
import com.tallerwebi.infraestructura.RepositorioPublicacion;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ServicioPublicacionImpl implements ServicioPublicacion {

    private final RepositorioPublicacion repositorioPublicacion;
    private final RepositorioComunidad repositorioComunidad;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioPublicacionImpl(RepositorioPublicacion repositorioPublicacion,
                                 RepositorioComunidad repositorioComunidad,
                                 RepositorioUsuario repositorioUsuario){
        this.repositorioPublicacion = repositorioPublicacion;
        this.repositorioComunidad = repositorioComunidad;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    @Transactional
    public void crearPublicacion(Publicacion publicacion, Long comunidadId, Usuario usuario) {
        Comunidad comunidad = repositorioComunidad.obtenerPorId(comunidadId);

        // Validaciones básicas
        if (comunidad == null || usuario == null || publicacion.getContenido() == null || publicacion.getContenido().isEmpty()) {
            return;
        }

        // Crear nueva publicación con datos asociados
        Publicacion nuevaPublicacion = new Publicacion(publicacion.getContenido(), usuario, comunidad);
        nuevaPublicacion.setImagen(publicacion.getImagen());
        nuevaPublicacion.setFechaCreacion(LocalDateTime.now());

        // Guardar en base de datos
        repositorioPublicacion.guardar(nuevaPublicacion);
    }

    @Override
    public List<Publicacion> listarPublicacionesPorComunidad(Long comunidadId) {
        return repositorioPublicacion.obtenerPorComunidad(comunidadId);
    }

    public List<PublicacionDTO> listarPublicacionesDTOPorComunidad(Long comunidadId){
        List<Publicacion> publicaciones = repositorioPublicacion.obtenerPorComunidad(comunidadId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return publicaciones.stream().map(pub -> {
            PublicacionDTO dto = new PublicacionDTO();
            dto.setContenido(pub.getContenido());
            dto.setAutorNombre(
                    pub.getAutorPublicacion() != null ? pub.getAutorPublicacion().getNombre() : "UsuarioPrueba");
            dto.setFechaFormateada(pub.getFechaCreacion() != null
                    ? pub.getFechaCreacion().format(formatter)
                    : "hace unos minutos");
            dto.setImagen(pub.getImagen());
            return dto;
        }).collect(Collectors.toList());
    }
}
