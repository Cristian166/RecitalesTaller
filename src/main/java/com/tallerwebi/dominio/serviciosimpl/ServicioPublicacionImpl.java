package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Publicacion;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.PublicacionDTO;
import com.tallerwebi.infraestructura.RepositorioComunidad;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioNotificacion;
import com.tallerwebi.infraestructura.RepositorioPublicacion;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ServicioPublicacionImpl implements ServicioPublicacion {

    private final RepositorioPublicacion repositorioPublicacion;
    private final RepositorioComunidad repositorioComunidad;
    private final RepositorioUsuario repositorioUsuario;
    private ServicioInsignia servicioInsignia;
    private RepositorioInsignia repositorioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    private RepositorioNotificacion repositorioNotificacion;

    public ServicioPublicacionImpl(RepositorioPublicacion repositorioPublicacion,
            RepositorioComunidad repositorioComunidad,
            RepositorioUsuario repositorioUsuario) {
        this.repositorioPublicacion = repositorioPublicacion;
        this.repositorioComunidad = repositorioComunidad;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Autowired
    public ServicioPublicacionImpl(RepositorioPublicacion repositorioPublicacion,
            RepositorioComunidad repositorioComunidad,
            RepositorioUsuario repositorioUsuario, RepositorioInsignia repositorioInsignia,
            ServicioInsignia servicioInsignia, RepositorioUsuarioInsignia repositorioUsuarioInsignia,
            RepositorioNotificacion repositorioNotificacion) {
        this.repositorioPublicacion = repositorioPublicacion;
        this.repositorioComunidad = repositorioComunidad;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioInsignia = repositorioInsignia;
        this.servicioInsignia = servicioInsignia;
        this.repositorioUsuarioInsignia = repositorioUsuarioInsignia;
        this.repositorioNotificacion = repositorioNotificacion;
    }

    @Override
    @Transactional
    public Boolean crearPublicacion(Publicacion publicacion, Long comunidadId, Usuario usuario) {
        Comunidad comunidad = repositorioComunidad.obtenerComunidadPorId(comunidadId);

        if (comunidad == null || usuario == null || publicacion.getContenido() == null
                || publicacion.getContenido().isEmpty()) {
            return false;
        }

        Publicacion nuevaPublicacion = new Publicacion(publicacion.getContenido(), usuario, comunidad);
        nuevaPublicacion.setImagen(publicacion.getImagen());
        nuevaPublicacion.setFechaCreacion(LocalDateTime.now());

        Usuario usuarioActualizado = repositorioUsuario.buscarId(usuario.getId());

        usuarioActualizado.setCantidadPublicaciones(usuarioActualizado.getCantidadPublicaciones() + 1);

        boolean yaTieneInsignia = repositorioUsuarioInsignia.existe(usuarioActualizado.getId(), 2L);

        if (usuarioActualizado.getCantidadPublicaciones() >= 5 && !yaTieneInsignia) {
            Insignia insigniaActivo = repositorioInsignia.obtenerPorId(2L);
            if (insigniaActivo != null) {
                servicioInsignia.asignarInsignia(usuarioActualizado, insigniaActivo);
            }
        }

        Set<Usuario> miembros = repositorioComunidad.obtenerMiembros(comunidadId);

        for (Usuario miembro : miembros) {
            if (!miembro.getId().equals(usuario.getId())) {

                Notificacion notificacion = new Notificacion();
                notificacion.setNombreNotificacion("Nueva publicación en " + comunidad.getNombre());
                notificacion.setDescripcionNotificacion(
                        usuario.getNombre() + " publicó algo nuevo en la comunidad ");
                notificacion.setLink("/comunidad/" + comunidadId);
                notificacion.setUsuario(miembro);

                repositorioNotificacion.agregarNotificacion(miembro, notificacion);
            }
        }

        repositorioUsuario.modificar(usuarioActualizado);

        return repositorioPublicacion.guardar(nuevaPublicacion);

    }

    @Override
    public List<Publicacion> listarPublicacionesPorComunidad(Long comunidadId) {
        return repositorioPublicacion.obtenerPorComunidad(comunidadId);
    }

    @Override
    public List<PublicacionDTO> listarPublicacionesDTOPorComunidad(Long comunidadId) {
        List<Publicacion> publicaciones = repositorioPublicacion.obtenerPorComunidad(comunidadId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<PublicacionDTO> listaDTO = new ArrayList<>();

        for (Publicacion pub : publicaciones) {
            PublicacionDTO dto = new PublicacionDTO();

            dto.setContenido(pub.getContenido());
            dto.setAutorNombre(
                    pub.getAutorPublicacion() != null ? pub.getAutorPublicacion().getNombre() : "UsuarioPrueba");

            dto.setFechaFormateada(
                    pub.getFechaCreacion() != null ? pub.getFechaCreacion().format(formatter) : "hace unos minutos");

            dto.setImagen(pub.getImagen());
            dto.setId(pub.getId());

            dto.setDestacada(pub.getDestacada());

            listaDTO.add(dto);
        }

        return listaDTO;
    }

    @Override
    public Publicacion buscarPublicacionPorId(Long pubId) {

        return repositorioPublicacion.obtenerPorId(pubId);

    }

}
