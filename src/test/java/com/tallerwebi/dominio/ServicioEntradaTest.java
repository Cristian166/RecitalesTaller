package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.serviciosimpl.ServicioEntradaImpl;
import com.tallerwebi.infraestructura.RepositorioEntrada;

public class ServicioEntradaTest {
    
    private ServicioEntradaImpl servicio;
    private RepositorioEntrada repositorioEntradaMock;

    @BeforeEach
    void setUp() {
        // Inicializa el mock de RepositorioEntrada
        repositorioEntradaMock = mock(RepositorioEntrada.class);
        servicio = new ServicioEntradaImpl(repositorioEntradaMock);  // Inyectamos el mock en el servicio

        List<Entrada> entradasMock = new ArrayList<>();
        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(entradasMock);

    }

    @Test
    public void debePoderCrearUnaentradaNueva(){    

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario(LocalTime.parse("21:00"));
        entrada1.setSeccion("Campo");

        doNothing().when(repositorioEntradaMock).guardarEntrada(any(Entrada.class));

        servicio.crearEntrada(entrada1);

        List<Entrada> entradasMock = new ArrayList<>();
        entradasMock.add(entrada1);
        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(entradasMock);

        assertTrue(servicio.obtenerTodasMisEntradas().contains(entrada1));

        assertNotNull(entrada1.getId());

        assertTrue(entrada1.getId() > 0);

    }

    @Test
    public void  debeObtenerTodasLasEntradas(){

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario(LocalTime.parse("21:00"));
        entrada1.setSeccion("Campo");

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setNombreRecital("Korn");
        entrada2.setLugar("Parque Sarmiento");
        entrada2.setFecha(LocalDate.parse("2026-04-15"));
        entrada2.setHorario(LocalTime.parse("21:00"));
        entrada2.setSeccion("Campo");

        doNothing().when(repositorioEntradaMock).guardarEntrada(any(Entrada.class));

        servicio.crearEntrada(entrada1);
        servicio.crearEntrada(entrada2);

        List<Entrada> entradasMock = new ArrayList<>();
        entradasMock.add(entrada1);
        entradasMock.add(entrada2);
        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(entradasMock);

        List<Entrada> entradas = servicio.obtenerTodasMisEntradas();

        assertTrue(entradas.contains(entrada1), "La lista contiene entrada1");
        assertTrue(entradas.contains(entrada2), "La lista contiene entrada2");
        
        assertEquals(2, entradas.size());

    }
    
    @Test
    public void debePonerEliminarUnaEntradaPorLaId(){

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario(LocalTime.parse("21:00"));
        entrada1.setSeccion("Campo");

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setNombreRecital("Korn");
        entrada2.setLugar("Parque Sarmiento");
        entrada2.setFecha(LocalDate.parse("2026-04-15"));
        entrada2.setHorario(LocalTime.parse("21:00"));
        entrada2.setSeccion("Campo");

        doNothing().when(repositorioEntradaMock).guardarEntrada(any(Entrada.class));
        servicio.crearEntrada(entrada1);
        servicio.crearEntrada(entrada2);
        
        doNothing().when(repositorioEntradaMock).eliminarPorId(anyLong());
        servicio.eliminarEntrada(1L);
        List<Entrada> entradas = servicio.obtenerTodasMisEntradas();

        assertTrue(entradas.stream().noneMatch( entrada -> entrada.getId().equals(1L)));

    }
        
}
