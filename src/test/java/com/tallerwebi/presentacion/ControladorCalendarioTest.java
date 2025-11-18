package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import com.tallerwebi.dominio.ServicioCalendario;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

public class ControladorCalendarioTest {

    private ServicioCalendario servicioCalendarioMock;
    private ControladorCalendario controladorCalendario;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {

        servicioCalendarioMock = mock(ServicioCalendario.class);
        controladorCalendario = new ControladorCalendario(servicioCalendarioMock);
        sessionMock = mock(HttpSession.class);
    }

    @Test
    public void mostrarCalendarioDeberiaDevolverVistaCalendarioConSusEventos() {
        
        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);


        List<Entrada> futuras = new ArrayList<>();
        List<Entrada> pasadas = new ArrayList<>();

        when(servicioCalendarioMock.obtenerEntradasFuturas(usuario)).thenReturn(futuras);
        when(servicioCalendarioMock.obtenerEntradasPasadas(usuario)).thenReturn(pasadas);

        ConcurrentModel model = new ConcurrentModel();

  
        String vista = controladorCalendario.mostrarCalendario(model, sessionMock);

    
        assertEquals("calendario", vista);

        assertEquals(usuario, model.getAttribute("usuario"));
        assertEquals(futuras, model.getAttribute("entradasFuturas"));
        assertEquals(pasadas, model.getAttribute("entradasPasadas"));

        verify(servicioCalendarioMock).obtenerEntradasFuturas(usuario);
        verify(servicioCalendarioMock).obtenerEntradasPasadas(usuario);

    }

    @Test
    public void mostrarCalendarioDebeRedirigirALoginSiNoHayUsuario() {

        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ConcurrentModel model = new ConcurrentModel();
        String vista = controladorCalendario.mostrarCalendario(model, sessionMock);

        
        assertEquals("redirect:/login", vista);

        verify(servicioCalendarioMock, never()).obtenerEntradasFuturas(any());
        verify(servicioCalendarioMock, never()).obtenerEntradasPasadas(any());
        
    }
}
