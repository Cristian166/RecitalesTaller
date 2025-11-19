package com.tallerwebi.dominio;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioNotificacionImpl;
import com.tallerwebi.infraestructura.RepositorioNotificacion;

public class ServicioNotificacionImplTest {

    private ServicioNotificacion servicioNotificacion;
    private RepositorioNotificacion repositorioNotificacionMock;
    private Usuario usuarioMock;

    @BeforeEach
    void init() {
        repositorioNotificacionMock = mock(RepositorioNotificacion.class);
        servicioNotificacion = new ServicioNotificacionImpl(repositorioNotificacionMock);  // Inyectamos el mock en el servicio
        usuarioMock= mock(Usuario.class);

        
    }

    @Test
    public void debePoderAgregarUnaNotificacion(){    

        Notificacion notificacion = new Notificacion();

        notificacion.setNombreNotificacion("Nueva entrada");
        notificacion.setDescripcionNotificacion("Se ha creado una nueva entrada");
        notificacion.setLink("/mis-entradas");
    

        servicioNotificacion.agregarNotificacion(usuarioMock, notificacion);

        verify(repositorioNotificacionMock, times(1)).agregarNotificacion(usuarioMock, notificacion);

    }


    @Test
    public void obtenerNotificacionesDeberiaDevolverVistaConNotificaciones() {
        // preparación
    
        Notificacion notificacion1 = new Notificacion();
        notificacion1.setNombreNotificacion("nueva entrada");
        Notificacion notificacion2 = new Notificacion();
        notificacion2.setNombreNotificacion("nueva insignia");

        List<Notificacion> notificaciones = new ArrayList<>();
        notificaciones.add(notificacion1);
        notificaciones.add(notificacion2);

        when(repositorioNotificacionMock.obtenerNotificacionesPorUsuario(usuarioMock)).thenReturn(notificaciones);

        // ejecución
        List<Notificacion> resultado = servicioNotificacion.obtenerNotificacionesPorUsuario(usuarioMock);

        // validación
        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0).getNombreNotificacion(), equalTo("nueva entrada"));
        assertThat(resultado.get(1).getNombreNotificacion(), equalTo("nueva insignia"));
        verify(repositorioNotificacionMock, times(1)).obtenerNotificacionesPorUsuario(usuarioMock);
    }

    @Test
    public void debePoderMarcarComoLeidasTodasLasNotificaciones(){    

        Notificacion notificacion1 = new Notificacion();
        notificacion1.setVista(false);
        Notificacion notificacion2 = new Notificacion();
        notificacion2.setVista(false);

        List<Notificacion> notificaciones = List.of(notificacion1, notificacion2);

        when(repositorioNotificacionMock.obtenerNotificacionesPorUsuario(usuarioMock))
                .thenReturn(notificaciones);
        servicioNotificacion.marcarTodasComoLeidas(usuarioMock);


        assertTrue(notificacion1.getVista());
        assertTrue(notificacion2.getVista());
        verify(repositorioNotificacionMock, times(1))
            .obtenerNotificacionesPorUsuario(usuarioMock);

    }
}
