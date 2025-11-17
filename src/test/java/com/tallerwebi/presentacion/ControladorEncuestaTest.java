package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioEncuesta;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

public class ControladorEncuestaTest {

    private ServicioEncuesta servicioEncuestaMock;
    private ServicioComunidad servicioComunidadMock;
    private HttpSession sessionMock;
    private ControladorEncuesta controlador;

    private Usuario usuario;
    private Comunidad comunidad;

    @BeforeEach
    void init() {
        servicioEncuestaMock = mock(ServicioEncuesta.class);
        servicioComunidadMock = mock(ServicioComunidad.class);
        sessionMock = mock(HttpSession.class);

        controlador = new ControladorEncuesta(
                servicioEncuestaMock,
                servicioComunidadMock,
                sessionMock);

        usuario = new Usuario();
        usuario.setId(1L);

        comunidad = new Comunidad();
        comunidad.setId(10L);
        comunidad.setUsuarioCreador(usuario);
    }


    @Test
    void dadoQueElUsuarioEsElCreadorEntoncesSeLeMuestraLaVistaCrearEncuesta() {
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);
        when(servicioComunidadMock.obtenerComunidad(10L)).thenReturn(comunidad);

        Model model = new ConcurrentModel();

        String vista = controlador.mostrarCrearEncuesta(10L, model);

        assertEquals("crear-encuesta", vista);
        assertTrue(model.containsAttribute("comunidad"));
    }

    @Test
    void dadoQueElUsuarioNoEsElCreadorEntoncesNoSeMuestraCrearEncuestaYSeRedirige() {
        Usuario otro = new Usuario();
        otro.setId(99L);

        when(sessionMock.getAttribute("usuario")).thenReturn(otro);
        when(servicioComunidadMock.obtenerComunidad(10L)).thenReturn(comunidad);

        String vista = controlador.mostrarCrearEncuesta(10L, new ConcurrentModel());

        assertEquals("redirect:/comunidad/10", vista);
    }


    @Test
    void dadoQueSeCreaUnaEncuestaEntoncesDeberiaCrearYRedirigir() {
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);
        when(servicioComunidadMock.obtenerComunidad(10L)).thenReturn(comunidad);

        List<String> opciones = Arrays.asList("A", "B");

        String vista = controlador.crearEncuesta(10L, "Pregunta?", opciones);

        verify(servicioEncuestaMock).crearEncuesta(comunidad, usuario, "Pregunta?", opciones);
        assertEquals("redirect:/comunidad/10", vista);
    }


    @Test
    void dadoQueSeVotaEntoncesDeberiaLlamarServicioYRedirigir() {
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        String vista = controlador.votar(5L, 10L);

        verify(servicioEncuestaMock).votar(5L, usuario);
        assertEquals("redirect:/comunidad/10", vista);
    }

    @Test
    void dadoQueSeEliminaUnaEncuestaEntoncesDeberiaEliminarYRedirigir() {
        String vista = controlador.eliminarEncuesta(7L, 10L);

        verify(servicioEncuestaMock).eliminarEncuesta(7L);
        assertEquals("redirect:/comunidad/10", vista);
    }
}
