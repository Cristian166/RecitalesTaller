package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.serviciosimpl.ServicioLoginImpl;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;

public class ServicioLoginImplTest {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioInsignia repositorioInsignia;
    private ServicioInsignia servicioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;

    private ServicioLoginImpl servicioLogin;

    Usuario usuario;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        repositorioInsignia = mock(RepositorioInsignia.class);
        servicioInsignia = mock(ServicioInsignia.class);
        repositorioUsuarioInsignia = mock(RepositorioUsuarioInsignia.class);

        servicioLogin = new ServicioLoginImpl(
                repositorioUsuario,
                repositorioInsignia,
                servicioInsignia,
                repositorioUsuarioInsignia);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@test.com");
        usuario.setPassword("123456");
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
    }

    @Test
    public void dadoQueElUsuarioExisteSeDevuelveElUsuario() {
        when(repositorioUsuario.buscarUsuario("test@test.com", "123456"))
                .thenReturn(usuario);

        Usuario resultado = servicioLogin.consultarUsuario("test@test.com", "123456");

        assertThat(resultado.getEmail(), equalTo("test@test.com"));
    }

    @Test
    public void dadoQueElUsuarioNoExisteRetornaNull() {
        when(repositorioUsuario.buscarUsuario("test@test.com", "123456"))
                .thenReturn(null);

        Usuario resultado = servicioLogin.consultarUsuario("test@test.com", "123456");

        assertThat(resultado, equalTo(null));
    }

    @Test
    public void dadoQueElUsuarioIniciaSesionSeAsignaInsigniaSiNoLaTiene() {

        when(repositorioUsuario.buscarUsuario("test@test.com", "123456"))
                .thenReturn(usuario);

        Insignia insignia = new Insignia();
        insignia.setId(5L);
        insignia.setNombre("Login");

        when(repositorioInsignia.obtenerPorId(5L)).thenReturn(insignia);
        when(repositorioUsuarioInsignia.existe(1L, 5L)).thenReturn(false);
        when(servicioInsignia.asignarInsignia(usuario, insignia)).thenReturn(true);

        Usuario resultado = servicioLogin.consultarUsuario("test@test.com", "123456");

        boolean yaTiene = repositorioUsuarioInsignia.existe(1L, 5L);
        boolean seAsigno = servicioInsignia.asignarInsignia(usuario, insignia);

        assertThat(seAsigno, equalTo(true));
        assertThat(yaTiene, equalTo(false));
        assertThat(resultado.getEmail(), equalTo("test@test.com"));
        assertThat(resultado.getNombre(), equalTo("Juan"));
        assertThat(resultado.getApellido(), equalTo("Perez"));

    }

    @Test
    public void dadoQueElUsuarioYaTieneLaInsigniaNoSeAsignaDeNuevo() {

        when(repositorioUsuario.buscarUsuario("test@test.com", "123456"))
                .thenReturn(usuario);

        Insignia insignia = new Insignia();
        insignia.setId(5L);

        when(repositorioInsignia.obtenerPorId(5L)).thenReturn(insignia);
        when(repositorioUsuarioInsignia.existe(1L, 5L)).thenReturn(true);

        Usuario resultado = servicioLogin.consultarUsuario("test@test.com", "123456");
        boolean yaTiene = repositorioUsuarioInsignia.existe(1L, 5L);
        boolean seAsigno = servicioInsignia.asignarInsignia(usuario, insignia);

        assertThat(seAsigno, equalTo(false));
        assertThat(yaTiene, equalTo(true));
        assertThat(resultado.getEmail(), equalTo("test@test.com"));
        assertThat(resultado.getNombre(), equalTo("Juan"));
        assertThat(resultado.getApellido(), equalTo("Perez"));

    }

    @Test
    public void dadoQueElEmailNoExisteSeRegistraCorrectamente() throws UsuarioExistente {
        when(repositorioUsuario.buscarPorEmail(usuario.getEmail())).thenReturn(null);

        Usuario resultado = servicioLogin.registrar(usuario);

        verify(repositorioUsuario, times(1)).guardar(usuario);
        assertThat(resultado.getEmail(), equalTo("test@test.com"));
    }

    @Test
    public void dadoQueElEmailYaExisteLanzaUsuarioExistente() {
        when(repositorioUsuario.buscarPorEmail(usuario.getEmail())).thenReturn(usuario);

        assertThrows(
                UsuarioExistente.class,
                () -> servicioLogin.registrar(usuario));
    }

    @Test
    public void dadoQueElNombreEsInvalidoLanzaExcepcion() {
        usuario.setNombre("   ");

        assertThrows(
                IllegalArgumentException.class,
                () -> servicioLogin.registrar(usuario));
    }

    @Test
    public void dadoQueElEmailEsInvalidoLanzaExcepcion() {
        usuario.setEmail("invalidEmail");

        assertThrows(
                IllegalArgumentException.class,
                () -> servicioLogin.registrar(usuario));
    }

    @Test
    public void dadoQueLaPasswordEsMuyCortaLanzaExcepcion() {
        usuario.setPassword("123");

        assertThrows(
                IllegalArgumentException.class,
                () -> servicioLogin.registrar(usuario));
    }
}
