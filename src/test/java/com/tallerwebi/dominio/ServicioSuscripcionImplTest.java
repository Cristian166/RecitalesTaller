package com.tallerwebi.dominio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioSuscripcionImpl;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioNotificacion;
import com.tallerwebi.infraestructura.RepositorioUsuario;

public class ServicioSuscripcionImplTest {

    private ServicioSuscripcion servicioSuscripcion;
    private ServicioInsignia servicioInsignia;
    private RepositorioInsignia repositorioInsignia;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioNotificacion repositorioNotificacion;

    private Usuario usuarioSesion;
    private Usuario usuarioBD;
    private Insignia insigniaPremium;

    @BeforeEach
    public void init() {

        repositorioInsignia = mock(RepositorioInsignia.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioInsignia = mock(ServicioInsignia.class);
        repositorioNotificacion= mock(RepositorioNotificacion.class);

        servicioSuscripcion = new ServicioSuscripcionImpl(
                repositorioUsuario,
                repositorioInsignia,
                repositorioNotificacion, servicioInsignia);

        usuarioSesion = mock(Usuario.class);
        when(usuarioSesion.getId()).thenReturn(1L);

        usuarioBD = new Usuario();
        usuarioBD.setId(1L);
        usuarioBD.setEsPremium(false);

        insigniaPremium = new Insignia();
        insigniaPremium.setId(8L);
    }

    @Test
    public void dadoQueElUsuarioPagaEntoncesQuedaMarcadoComoPremium() {

        when(usuarioSesion.getId()).thenReturn(1L); 
        when(repositorioInsignia.obtenerPorId(8L)).thenReturn(insigniaPremium);

        servicioSuscripcion.procesarPagoPremium(usuarioSesion);

        assertTrue(usuarioSesion.getEsPremium());
        verify(repositorioUsuario).modificar(usuarioSesion);
    }

    @Test
    public void dadoQueElUsuarioEsNullNoDebePasarNada() {

        servicioSuscripcion.procesarPagoPremium(null);
        assertThat(usuarioBD.getEsPremium(), is(false));
    }

    @Test
    public void siElUsuarioNoExisteEnBD_NoSeMarcaPremium() {

        when(repositorioUsuario.buscarId(1L)).thenReturn(null);

        servicioSuscripcion.procesarPagoPremium(usuarioSesion);

        assertThat(usuarioBD.getEsPremium(), is(false));
    }

    @Test
    public void siNoExisteLaInsigniaIgualSeMarcaPremium() {

        when(repositorioUsuario.buscarId(1L)).thenReturn(usuarioBD);
        when(repositorioInsignia.obtenerPorId(8L)).thenReturn(null);

        servicioSuscripcion.procesarPagoPremium(usuarioSesion);

        assertThat(usuarioBD.getEsPremium(), is(true));
    }

}
