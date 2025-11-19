package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioComunidad;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioPublicacion;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ServicioComunidadImpl implements ServicioComunidad {

    private final RepositorioComunidad repositorioComunidad;
    private ServicioInsignia servicioInsignia;
    private RepositorioInsignia repositorioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioPublicacion repositorioPublicacion;

    public ServicioComunidadImpl(RepositorioComunidad repositorioComunidad) {
        this.repositorioComunidad = repositorioComunidad;
    }

    @Autowired
    public ServicioComunidadImpl(RepositorioComunidad repositorioComunidad,
            RepositorioInsignia repositorioInsignia,
            ServicioInsignia servicioInsignia,
            RepositorioUsuario repositorioUsuario,
            RepositorioUsuarioInsignia repositorioUsuarioInsignia,
            RepositorioPublicacion repositorioPublicacion) {
        this.repositorioComunidad = repositorioComunidad;
        this.repositorioInsignia = repositorioInsignia;
        this.servicioInsignia = servicioInsignia;
        this.repositorioUsuarioInsignia = repositorioUsuarioInsignia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioPublicacion = repositorioPublicacion;
    }

    @Override
    @Transactional
    public Set<Comunidad> listarComunidadesUnidas(Long usuarioId) {
        return repositorioComunidad.obtenerComunidadesUnidas(usuarioId);
    }

    @Override
    @Transactional
    public Set<Comunidad> listarComunidadesSugeridas(Long usuarioId) {
        return repositorioComunidad.obtenerComunidadesSugeridas(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public Comunidad obtenerComunidad(Long id) {
        Comunidad comunidad = repositorioComunidad.obtenerComunidadPorId(id);
        System.out.println("Servicio comunidad obtenido: " + comunidad);
        return comunidad;
    }

    @Override
    @Transactional
    public void unirseAComunidad(Usuario usuario, Long comunidadId) {
        Comunidad comunidad = repositorioComunidad.obtenerComunidadPorId(comunidadId);

        if (comunidad != null && usuario != null) {
            if (!comunidad.getUsuarios().contains(usuario)) {
                comunidad.getUsuarios().add(usuario);
                repositorioComunidad.guardarUnaComunidad(comunidad);
            }
        }
    }

    @Override
    @Transactional
    public void abandonarComunidad(Usuario usuario, Long comunidadId) {
        repositorioComunidad.abandonarComunidad(usuario, comunidadId);
    }

    @Override
    @Transactional
    public Comunidad crearComunidad(Comunidad comunidad, Usuario usuario) {

        Usuario usuarioBuscado = repositorioUsuario.buscarId(usuario.getId());
        boolean yaTieneInsignia = repositorioUsuarioInsignia.existe(usuarioBuscado.getId(), 7L);

        if (!yaTieneInsignia) {
            Insignia insigniaCreadorComunidad = repositorioInsignia.obtenerPorId(7L);
            if (insigniaCreadorComunidad != null) {
                servicioInsignia.asignarInsignia(usuarioBuscado, insigniaCreadorComunidad);
            }

        }

        return repositorioComunidad.guardarUnaComunidad(comunidad);
    }

    @Override
    @Transactional
    public void eliminarComunidad(Long id) {
        repositorioComunidad.borrarComunidad(id);
    }

    @Transactional
    @Override
    public long contarMiembrosComunidad(Long comunidadId) {
        return repositorioComunidad.contarMiembrosDeComunidad(comunidadId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existeComunidadPorNombre(String nombre) {
        return repositorioComunidad.obtenerComunidadPorNombre(nombre) != null;
    }

    @Override
    @Transactional
    public void destacarPublicacion(Long idComunidad, Long idPublicacion, Usuario actual) {
        Comunidad comunidad = repositorioComunidad.obtenerComunidadPorId(idComunidad);
        Publicacion publicacion = repositorioPublicacion.obtenerPorId(idPublicacion);

        if (comunidad == null) {
            throw new RuntimeException("La comunidad no existe");
        }

        if (comunidad.getPublicacionDestacada() != null) {
            Publicacion anterior = comunidad.getPublicacionDestacada();
            anterior.setDestacada(false);
            repositorioPublicacion.guardar(anterior);
        }

        publicacion.setDestacada(true);
        comunidad.setPublicacionDestacada(publicacion);

        Usuario autor = publicacion.getAutorPublicacion();

        Long idInsigniaDestacado = 3L;

        boolean yaLaTiene = repositorioUsuarioInsignia.existe(autor.getId(), idInsigniaDestacado);

        if (!yaLaTiene) {
            Insignia insignia = repositorioInsignia.obtenerPorId(idInsigniaDestacado);

            if (insignia != null) {
                servicioInsignia.asignarInsignia(autor, insignia);
            }
        }

        repositorioPublicacion.guardar(publicacion);
        repositorioComunidad.guardarUnaComunidad(comunidad);
    }

    @Override
    @Transactional
    public void quitarDestacado(Long idComunidad, Long idPublicacion, Usuario actual) {
        Comunidad comunidad = repositorioComunidad.obtenerComunidadPorId(idComunidad);
        Publicacion publicacion = repositorioPublicacion.obtenerPorId(idPublicacion);

        if (comunidad == null) {
            throw new RuntimeException("La comunidad no existe");
        }

        if (!comunidad.getUsuarioCreador().getId().equals(actual.getId())) {
            throw new RuntimeException("No tenés permiso para quitar destacados");
        }

        if (comunidad.getPublicacionDestacada() != null &&
                comunidad.getPublicacionDestacada().getId().equals(idPublicacion)) {

            comunidad.setPublicacionDestacada(null);
            publicacion.setDestacada(false);

            repositorioPublicacion.guardar(publicacion);
            repositorioComunidad.guardarUnaComunidad(comunidad);
        }
    }

}
