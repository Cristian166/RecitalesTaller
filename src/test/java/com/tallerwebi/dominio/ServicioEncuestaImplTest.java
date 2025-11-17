package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.OpcionEncuesta;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.Voto;
import com.tallerwebi.dominio.serviciosimpl.ServicioEncuestaImpl;
import com.tallerwebi.infraestructura.RepositorioEncuesta;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioOpcionEncuesta;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;
import com.tallerwebi.infraestructura.RepositorioVoto;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ServicioEncuestaImplTest {

    private RepositorioEncuesta repoEncuesta;
    private RepositorioOpcionEncuesta repoOpciones;
    private RepositorioVoto repoVoto;
    private ServicioInsignia servicioInsignia;
    private RepositorioInsignia repoInsignia;
    private RepositorioUsuario repoUsuario;
    private RepositorioUsuarioInsignia repoUsuarioInsignia;

    private ServicioEncuestaImpl servicio;

    private Usuario usuario;
    private Comunidad comunidad;

    @BeforeEach
    public void init() {
        repoEncuesta = mock(RepositorioEncuesta.class);
        repoOpciones = mock(RepositorioOpcionEncuesta.class);
        repoVoto = mock(RepositorioVoto.class);
        servicioInsignia = mock(ServicioInsignia.class);
        repoInsignia = mock(RepositorioInsignia.class);
        repoUsuario = mock(RepositorioUsuario.class);
        repoUsuarioInsignia = mock(RepositorioUsuarioInsignia.class);

        servicio = new ServicioEncuestaImpl(
                repoEncuesta,
                repoOpciones,
                repoVoto,
                servicioInsignia,
                repoInsignia,
                repoUsuario,
                repoUsuarioInsignia);

        usuario = new Usuario();
        usuario.setId(1L);

        comunidad = new Comunidad();
        comunidad.setId(5L);
    }

    @Test
    public void dadoQueYaExisteUnaEncuestaEstaSeDesactivaYSeCreaUnaNueva() {
        Encuesta encuestaActiva = new Encuesta();
        encuestaActiva.setActiva(true);

        when(repoEncuesta.obtenerEncuestaActiva(5L)).thenReturn(encuestaActiva);
        when(repoUsuario.buscarId(1L)).thenReturn(usuario);
        when(repoUsuarioInsignia.existe(1L, 6L)).thenReturn(true);

        List<String> opciones = Arrays.asList("Sí", "No");

        Encuesta nueva = servicio.crearEncuesta(comunidad, usuario, "probando", opciones);

        assertEquals("probando", nueva.getPregunta());
        assertTrue(nueva.isActiva());
        assertFalse(encuestaActiva.isActiva());
    }

    @Test
    public void dadoQueElUsuarioNoTieneInsigniaEntoncesSeLeAsigna() {
        when(repoEncuesta.obtenerEncuestaActiva(5L)).thenReturn(null);
        when(repoUsuario.buscarId(1L)).thenReturn(usuario);
        when(repoUsuarioInsignia.existe(1L, 6L)).thenReturn(false);

        Insignia insignia = new Insignia();
        when(repoInsignia.obtenerPorId(6L)).thenReturn(insignia);

        List<String> opciones = Arrays.asList("A", "B");

        Encuesta encuesta = servicio.crearEncuesta(comunidad, usuario, "Pregunta", opciones);

        assertEquals("Pregunta", encuesta.getPregunta());
        assertTrue(encuesta.isActiva());
    }

    @Test
    public void dadoQueLaOpcionNoExisteEntoncesNoSePuedeVotar() {
        when(repoOpciones.obtenerPorId(99L)).thenReturn(null);
        Boolean seVoto = servicio.votar(99L, usuario);

        assertFalse(seVoto);
    }

    @Test
    public void dadoQueElUsuarioNoVotoAunEntoncesSePuedeRegistrarSuVoto() {
        OpcionEncuesta opcion = new OpcionEncuesta();
        opcion.setVotos(0);

        Encuesta encuesta = new Encuesta();
        encuesta.setId(10L);
        opcion.setEncuesta(encuesta);

        when(repoOpciones.obtenerPorId(5L)).thenReturn(opcion);
        when(repoVoto.obtenerVotoDeUsuario(1L, 10L)).thenReturn(null);

        Boolean seVoto = servicio.votar(5L, usuario);

        assertEquals(1, opcion.getVotos());
        assertTrue(seVoto);
    }

    @Test
    public void dadoQueElUsuarioYavotoSeCambiaSuVoto() {
        OpcionEncuesta opcionAnterior = new OpcionEncuesta();
        opcionAnterior.setVotos(3);

        Voto votoExistente = new Voto();
        votoExistente.setUsuario(usuario);
        votoExistente.setOpcion(opcionAnterior);

        Encuesta encuesta = new Encuesta();
        encuesta.setId(10L);
        opcionAnterior.setEncuesta(encuesta);

        when(repoVoto.obtenerVotoDeUsuario(1L, 10L)).thenReturn(votoExistente);

        OpcionEncuesta nueva = new OpcionEncuesta();
        nueva.setVotos(10);
        nueva.setEncuesta(encuesta);

        when(repoOpciones.obtenerPorId(5L)).thenReturn(nueva);

        Boolean seVoto = servicio.votar(5L, usuario);

        assertTrue(seVoto);
        assertEquals(2, opcionAnterior.getVotos());
        assertEquals(11, nueva.getVotos());
    }

    @Test
    public void dadoQueNoExisteUnaEncuestaNoSeBorraNada() {

        when(repoEncuesta.obtenerPorId(10L)).thenReturn(null);

        Boolean seBorro = servicio.eliminarEncuesta(10L);

        assertThat(seBorro, is(equalTo(false)));
    }

    @Test
    public void siLaEncuestaExiste_seEliminaCorrectamente() {
        Encuesta encuesta = new Encuesta();
        encuesta.setId(20L);

        when(repoEncuesta.obtenerPorId(20L)).thenReturn(encuesta);

        Boolean seBorro = servicio.eliminarEncuesta(20L);

        assertThat(seBorro, is(equalTo(true)));
    }

}
