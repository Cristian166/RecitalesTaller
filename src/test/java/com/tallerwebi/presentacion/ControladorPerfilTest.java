package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.entidades.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorPerfilTest {

    private ServicioPerfil servicioPerfilMock;
    private ControladorPerfil controladorPerfil;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioPerfilMock = mock(ServicioPerfil.class);
        controladorPerfil = new ControladorPerfil(servicioPerfilMock);
        sessionMock = mock(HttpSession.class);
    }

    @Test
    public void irAPerfilSinUsuarioEnSesionDeberiaRedirigirALogin() {
        // no hay usuario en la sesión
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorPerfil.irAPerfil(sessionMock);

        assertEquals("redirect:/login", modelAndView.getViewName());
    }

    @Test
    public void irAPerfilConUsuarioEnSesionDeberiaDevolverVistaPerfil() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Nicolas");
        usuario.setApellido("Oliverio");
        usuario.setEmail("nico@mail.com");

        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        ModelAndView modelAndView = controladorPerfil.irAPerfil(sessionMock);

        assertEquals("perfil", modelAndView.getViewName());
        assertEquals("Nicolas", modelAndView.getModel().get("nombre"));
        assertEquals("nico@mail.com", modelAndView.getModel().get("email"));
    }

    @Test
    public void irAEditarDeberiaDevolverVistaEditarPreferencias() {
        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        ModelAndView modelAndView = controladorPerfil.irAEditar(sessionMock);

        assertEquals("editar-preferencias", modelAndView.getViewName());
        verify(servicioPerfilMock).obtenerGeneros();
        verify(servicioPerfilMock).obtenerArtistas();
        verify(servicioPerfilMock).obtenerRegiones();
        verify(servicioPerfilMock).obtenerEpocas();
    }

    @Test
    public void guardarPreferenciasDeberiaGuardarYRedirigirAPerfil() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        List<String> generos = List.of("Rock", "Pop");
        List<String> artistas = List.of("Queen", "Bowie");
        List<String> regiones = List.of("Italia", "Francia");
        List<String> epocas = List.of("70's", "80's");

        ModelAndView modelAndView = controladorPerfil.guardarPreferencias(
                sessionMock, generos, artistas, regiones, epocas
        );

        // Verificamos que el servicio haya sido llamado correctamente
        verify(servicioPerfilMock).guardarPreferencias(
                eq(1L), eq(generos), eq(artistas), eq(regiones), eq(epocas)
        );

        assertEquals("redirect:/perfil", modelAndView.getViewName());
    }
}
