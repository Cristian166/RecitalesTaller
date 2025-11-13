package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioPublicacionImpl;
import com.tallerwebi.infraestructura.RepositorioComunidad;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioPublicacion;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioPublicacionImplTest {

    private RepositorioPublicacion repoPublicacion;
    private RepositorioComunidad repoComunidad;
    private RepositorioUsuario repoUsuario;
    private RepositorioInsignia repoInsignia;
    private ServicioInsignia servicioInsignia;
    private RepositorioUsuarioInsignia repoUsuarioInsignia;

    private ServicioPublicacionImpl servicio;

    private Usuario usuario;
    private Publicacion publicacion;

    @BeforeEach
    public void init() {
        repoPublicacion = mock(RepositorioPublicacion.class);
        repoComunidad = mock(RepositorioComunidad.class);
        repoUsuario = mock(RepositorioUsuario.class);
        repoInsignia = mock(RepositorioInsignia.class);
        servicioInsignia = mock(ServicioInsignia.class);
        repoUsuarioInsignia = mock(RepositorioUsuarioInsignia.class);
        usuario = crearUsuario();
        publicacion = crearPublicacion();

        servicio = new ServicioPublicacionImpl(
                repoPublicacion,
                repoComunidad,
                repoUsuario,
                repoInsignia,
                servicioInsignia,
                repoUsuarioInsignia);
    }

    @Test
    public void dadoQueNoExisteUnaComunidadNoCreaLaPublicacion() {

        publicacion = crearPublicacion();

        when(repoComunidad.obtenerComunidadPorId(10L)).thenReturn(null);

        Boolean seCreo = servicio.crearPublicacion(publicacion, 10L, usuario);

        assertTrue(!seCreo);
    }

    @Test
    public void dadoQueElUsuarioEsValidoSeCreaLaPublicacion() {

        usuario.setCantidadPublicaciones(3);

        Comunidad comunidad = new Comunidad();

        when(repoComunidad.obtenerComunidadPorId(5L)).thenReturn(comunidad);
        when(repoUsuario.buscarId(1L)).thenReturn(usuario);
        when(repoUsuarioInsignia.existe(1L, 2L)).thenReturn(false);

        servicio.crearPublicacion(publicacion, 5L, usuario);

        assertEquals(4, usuario.getCantidadPublicaciones().intValue());
    }

    @Test
    public void dadoQueElUsuarioHizo5PublicacionesEntoncesSeLeAsignaLaInsignia() {

        usuario.setCantidadPublicaciones(5);

        Comunidad comunidad = new Comunidad();
        Insignia insignia = new Insignia();

        when(repoComunidad.obtenerComunidadPorId(3L)).thenReturn(comunidad);
        when(repoUsuario.buscarId(1L)).thenReturn(usuario);
        when(repoUsuarioInsignia.existe(1L, 2L)).thenReturn(false);
        when(repoInsignia.obtenerPorId(2L)).thenReturn(insignia);
        when(repoPublicacion.guardar(any())).thenReturn(true);

        Boolean seCrea = servicio.crearPublicacion(publicacion, 3L, usuario);

        assertEquals(true, seCrea);
        assertEquals(6, usuario.getCantidadPublicaciones().intValue());
    }

    @Test
    public void dadoQueYaTieneLaInsigniaEntoncesNoSeLeAsignaNuevamente() {

        usuario.setCantidadPublicaciones(6);

        Comunidad comunidad = new Comunidad();
        Insignia insignia = new Insignia();

        when(repoComunidad.obtenerComunidadPorId(2L)).thenReturn(comunidad);
        when(repoUsuario.buscarId(1L)).thenReturn(usuario);
        when(repoUsuarioInsignia.existe(1L, 2L)).thenReturn(true);
        when(repoInsignia.obtenerPorId(2L)).thenReturn(insignia);
        when(repoPublicacion.guardar(any())).thenReturn(true);

        Boolean seCreo = servicio.crearPublicacion(publicacion, 2L, usuario);

        assertEquals(7, usuario.getCantidadPublicaciones().intValue());
        assertEquals(true, seCreo);
    }

    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        return usuario;
    }

    private Publicacion crearPublicacion() {
        Publicacion pub = new Publicacion();
        pub.setContenido("Publicacion");
        return pub;
    }

}
