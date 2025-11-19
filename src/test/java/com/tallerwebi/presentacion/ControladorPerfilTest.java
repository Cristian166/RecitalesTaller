package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ControladorPerfilTest {

    private ServicioPerfil servicioPerfilMock;
    private ServicioInsignia servicioInsigniaMock;
    private ControladorPerfil controladorPerfil;
    private HttpSession sessionMock;
    private Model model;
    @BeforeEach
    public void init() {
        servicioPerfilMock = mock(ServicioPerfil.class);
        servicioInsigniaMock = mock(ServicioInsignia.class);
        controladorPerfil = new ControladorPerfil(servicioPerfilMock, servicioInsigniaMock);
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

    @Test
    public void debePoderVerseLasInsigniasConseguidasEnElPerfil(){

        Usuario usuario = new Usuario();
        usuario.setNombre("Nico");
        usuario.setApellido("Oliverio");
        usuario.setEmail("nico@example.com");

        Insignia insignia1 = new Insignia();    
        Insignia insignia2 = new Insignia();

        List<Insignia> insigniasDelUsuario = List.of(insignia1, insignia2);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);
        when(servicioInsigniaMock.obtenerInsigniasDeUsuario(usuario)).thenReturn(insigniasDelUsuario);
        when(servicioPerfilMock.obtenerPreferenciasPorUsuario(usuario)).thenReturn(null);


        ModelAndView modelAndView = controladorPerfil.irAPerfil(sessionMock);
        assertEquals("perfil", modelAndView.getViewName());

        List<Insignia> insigniasEnModelo = (List<Insignia>) modelAndView.getModel().get("insignias");
        assertNotNull(insigniasEnModelo);
        assertEquals(2, insigniasEnModelo.size());
        assertTrue(insigniasEnModelo.containsAll(List.of(insignia1, insignia2)));

        verify(servicioInsigniaMock).obtenerInsigniasDeUsuario(usuario);
    }

    @Test
    public void debeVisitarUnPerfilDeOtroUsuario(){

        Model modelMock = mock(Model.class);
        
        Usuario usuarioActual = new Usuario();
        usuarioActual.setId(1L);
        usuarioActual.setNombre("Nahuel");
        usuarioActual.setApellido("Tacacho");
        usuarioActual.setEmail("nahuel@example.com");

        Long idUsuarioVisitado = 2L;
        Usuario usuarioVisitado = new Usuario();
        usuarioVisitado.setId(idUsuarioVisitado);
        usuarioVisitado.setNombre("Carlos");
        usuarioVisitado.setApellido("Gomez");
        usuarioVisitado.setEmail("carlos@example.com");

        PreferenciaUsuario preferenciasVisitado = new PreferenciaUsuario();
        preferenciasVisitado.setGenerosSeleccionados(List.of("Pop", "Rock"));
        preferenciasVisitado.setArtistasSeleccionados(List.of("Queen", "Bowie"));
        preferenciasVisitado.setRegionesSeleccionadas(List.of("Argentina"));
        preferenciasVisitado.setEpocasSeleccionadas(List.of("80s", "90s"));

        Insignia insignia1 = new Insignia();
        Insignia insignia2 = new Insignia();
        List<Insignia> insigniasVisitado = List.of(insignia1, insignia2);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioActual);
        when(servicioPerfilMock.obtenerUsuarioPorId(idUsuarioVisitado)).thenReturn(usuarioVisitado);
        when(servicioPerfilMock.obtenerPreferenciasPorUsuario(usuarioVisitado)).thenReturn(preferenciasVisitado);
        when(servicioInsigniaMock.obtenerInsigniasDeUsuario(usuarioVisitado)).thenReturn(insigniasVisitado);

        String vistaRedireccionada = controladorPerfil.verPerfilVisitado(idUsuarioVisitado, sessionMock, modelMock);

        verify(modelMock).addAttribute("usuarioVisitado", usuarioVisitado);
        verify(modelMock).addAttribute("usuarioActual", usuarioActual);
        verify(modelMock).addAttribute("insignias", insigniasVisitado);
        verify(modelMock).addAttribute("generos", preferenciasVisitado.getGenerosSeleccionados());
        verify(modelMock).addAttribute("artistas", preferenciasVisitado.getArtistasSeleccionados());
        verify(modelMock).addAttribute("regiones", preferenciasVisitado.getRegionesSeleccionadas());
        verify(modelMock).addAttribute("epocas", preferenciasVisitado.getEpocasSeleccionadas());

        assertEquals("perfil-visitado", vistaRedireccionada);
    }
}