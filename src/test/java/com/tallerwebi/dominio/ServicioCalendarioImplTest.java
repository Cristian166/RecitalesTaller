package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioCalendarioImpl;
import com.tallerwebi.infraestructura.RepositorioCalendario;


public class ServicioCalendarioImplTest {

    private ServicioCalendario servicioCalendario;
    private RepositorioCalendario repositorioCalendarioMock;
    private Usuario usuarioMock;

    @BeforeEach
    void init() {
        repositorioCalendarioMock = mock(RepositorioCalendario.class);
        servicioCalendario = new ServicioCalendarioImpl(repositorioCalendarioMock);  // Inyectamos el mock en el servicio
        usuarioMock= mock(Usuario.class);
    }

    @Test
    public void obtenerEntradasFuturasDebeDevolverEntradasDesdeHoyEnAdelante() {
        // Arrange
        Entrada pasada = new Entrada();
        pasada.setFecha(LocalDate.of(2025, 1, 10));
        Entrada futura1 = new Entrada();
        futura1.setFecha(LocalDate.of(2026, 10, 20));
        Entrada futura2 = new Entrada();
        futura2.setFecha(LocalDate.of(2026, 1, 20));


        List<Entrada> entradas = Arrays.asList(futura2, pasada, futura1);

        when(repositorioCalendarioMock.obtenerEventos(usuarioMock)).thenReturn(entradas);

        List<Entrada> resultado = servicioCalendario.obtenerEntradasFuturas(usuarioMock);

        assertEquals(2, resultado.size());

        // Debe estar ordenado: hoy, +1 día, +5 días
        assertEquals(LocalDate.of(2026, 1, 20), resultado.get(0).getFecha());
        assertEquals(LocalDate.of(2026, 10, 20), resultado.get(1).getFecha());

        verify(repositorioCalendarioMock, times(1)).obtenerEventos(usuarioMock);
    }


    @Test
    public void obtenerEntradasPasadasDebeDevolverEntradasDeFechasPasadas() {
        // Arrange

        Entrada pasada = new Entrada();
        pasada.setFecha(LocalDate.of(2025, 1, 10));
        Entrada pasada2 = new Entrada();
        pasada2.setFecha(LocalDate.of(2024, 1, 20));
        Entrada futura1 = new Entrada();
        futura1.setFecha(LocalDate.of(2026, 10, 20));
        


        List<Entrada> entradas = Arrays.asList(pasada2, pasada, futura1);

        when(repositorioCalendarioMock.obtenerEventos(usuarioMock)).thenReturn(entradas);

        List<Entrada> resultado = servicioCalendario.obtenerEntradasPasadas(usuarioMock);

        assertEquals(2, resultado.size());

        // Debe estar ordenado: hoy, +1 día, +5 días
        assertEquals(LocalDate.of(2025, 1, 10), resultado.get(0).getFecha());
        assertEquals(LocalDate.of(2024, 1, 20), resultado.get(1).getFecha());

        verify(repositorioCalendarioMock, times(1)).obtenerEventos(usuarioMock);
    }

    @Test
    public void obtenerEventosDeberiaDevolverVistaConLosEventos() {
        Usuario usuario = new Usuario();

        Entrada entrada1 = new Entrada();
        Entrada entrada2 = new Entrada();

        List<Entrada> listaMock = Arrays.asList(entrada1, entrada2);

        when(repositorioCalendarioMock.obtenerEventos(usuario)).thenReturn(listaMock);

        List<Entrada> resultado = servicioCalendario.obtenerEventos(usuario);

        assertEquals(listaMock, resultado);            

        verify(repositorioCalendarioMock, times(1)).obtenerEventos(usuario);            
    }


    
}
