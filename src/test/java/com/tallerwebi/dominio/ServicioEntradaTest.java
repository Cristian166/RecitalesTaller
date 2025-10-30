package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.serviciosimpl.ServicioEntradaImpl;

public class ServicioEntradaTest {
    
    @Test
    public void debePoderCrearUnaentradaNueva(){
        ServicioEntradaImpl servicio = new ServicioEntradaImpl();

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario(LocalTime.parse("21:00"));
        entrada1.setSeccion("Campo");

        servicio.crearEntrada(entrada1);

        assertTrue(servicio.obtenerTodasMisEntradas().contains(entrada1));

        assertNotNull(entrada1.getId());

        assertTrue(entrada1.getId() > 0);

    }

    @Test
    public void  debeObtenerTodasLasEntradas(){
        ServicioEntradaImpl servicio = new ServicioEntradaImpl();

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

        servicio.crearEntrada(entrada1);
        servicio.crearEntrada(entrada2);

        List<Entrada> entradas = servicio.obtenerTodasMisEntradas();

        assertTrue(entradas.contains(entrada1), "La lista contiene entrada1");
        assertTrue(entradas.contains(entrada2), "La lista contiene entrada2");
        
        assertEquals(2, entradas.size());

    }
    
    @Test
    public void debePonerEliminarUnaEntradaPorLaId(){
        ServicioEntradaImpl servicio = new ServicioEntradaImpl();

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

        servicio.crearEntrada(entrada1);
        servicio.crearEntrada(entrada2);
        
        List<Entrada> entradas = servicio.obtenerTodasMisEntradas();

        assertTrue(entradas.stream().noneMatch( entrada -> entrada.getId().equals(1L)));

    }
        
}
