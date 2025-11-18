package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;


import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.entidades.Usuario;

public class ControladorNotificacionTest {
    private ServicioNotificacion servicioNotificacion;
    private ControladorNotificacion controladorNotificacion;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {

        servicioNotificacion = mock(ServicioNotificacion.class);
        controladorNotificacion = new ControladorNotificacion(servicioNotificacion);
        sessionMock = mock(HttpSession.class);
    }

    @Test
    public void irANotificacionDeberiaDevolverVistaNotificaciones() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre("Nicolas");

        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);
        ConcurrentModel model = new ConcurrentModel();

       
        String vista = controladorNotificacion.mostrarNotificaciones(model, sessionMock);

        assertEquals("notificaciones", vista);

    }
    @Test
    public void marcarTodasComoLeidasDeberiaUsarElMetodoYRedirigirANotificaciones() {
        
        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);
        doNothing().when(servicioNotificacion).marcarTodasComoLeidas(usuario);

        String vista = controladorNotificacion.marcarTodasComoLeidas(sessionMock);

        assertEquals("redirect:/notificaciones", vista);
        verify(servicioNotificacion, times(1)).marcarTodasComoLeidas(usuario);
    }
    
}