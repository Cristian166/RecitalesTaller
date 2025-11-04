package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

public class ControladorEntradaTest {
    
    @Test
    public void debeMostrarMisEntradasYMostrarLaVistaCorrecta(){

        ServicioEntrada servicioMock = mock(ServicioEntrada.class);
        HttpSession sessionMock = mock(HttpSession.class);
        ControladorEntrada controlador = new ControladorEntrada(servicioMock);

        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        Entrada entrada1 = new Entrada();

        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");

        Entrada entrada2 = new Entrada();

        entrada2.setId(1L);
        entrada2.setNombreRecital("Dark Tranquility");
        entrada2.setLugar("El teatrito");
        entrada2.setFecha(LocalDate.parse("2026-01-15"));
        entrada2.setHorario("21:00");
        entrada2.setSeccion("Campo");


        List<Entrada> entradasSimuladas = new ArrayList<>();
        entradasSimuladas.add(entrada1);
        entradasSimuladas.add(entrada2);

        when( servicioMock.obtenerEntradasPorUsuario(usuario)).thenReturn(entradasSimuladas);

        Model modelMock = mock(Model.class);

        String vistaRetornada = controlador.mostrarMisEntradas(modelMock, sessionMock);

        verify(servicioMock).obtenerEntradasPorUsuario(usuario);
        verify(modelMock).addAttribute("entradas",entradasSimuladas);

        assertEquals("vista-entradas-recitales", vistaRetornada);
    }

    @Test
    public void debePoderAgregarUnaEntradaYRedirigaAVistaDeLasEntradas(){
        
        ServicioEntrada servicioMock = mock(ServicioEntrada.class);
        HttpSession sessionMock = mock(HttpSession.class);
        ControladorEntrada controlador = new ControladorEntrada(servicioMock);

        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");

        String vistaDevuelta = controlador.agregarEntrada(entrada1, null, sessionMock);

        verify(servicioMock).crearEntrada(entrada1, usuario);
        assertEquals("redirect:/vista-entradas-recitales", vistaDevuelta);
        
    }

    @Test
    public void debePoderEliminarUnaEntradaPorLaIdEnLaMismaVista(){

        ServicioEntrada servicioMock = mock(ServicioEntrada.class);
        ControladorEntrada controlador = new ControladorEntrada(servicioMock);

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setNombreRecital("Korn");
        entrada2.setLugar("Parque Sarmiento");
        entrada2.setFecha(LocalDate.parse("2026-04-15"));
        entrada2.setHorario("21:00");
        entrada2.setSeccion("Campo");

        List<Entrada> entradasSimuladas = new ArrayList<>( Arrays.asList(entrada1,entrada2));
        when(servicioMock.obtenerTodasMisEntradas()).thenReturn(entradasSimuladas);

        doAnswer(invocation -> {
        Long id = invocation.getArgument(0);  // Obtener el id que se pasa al método
        entradasSimuladas.removeIf(entrada -> entrada.getId().equals(id));  // Eliminar la entrada correspondiente
        return null;
        }).when(servicioMock).eliminarEntrada(1L);

        controlador.eliminarEntrada(1L);

        verify(servicioMock).eliminarEntrada(1L);

        assertEquals(1, entradasSimuladas.size());
        assertTrue(entradasSimuladas.stream().noneMatch(entrada -> entrada.getId().equals(1L)));
        
    }

    @Test
    public void debePoderMostrarVistaDelFormularioParaCrearUnaEntrada(){
        ServicioEntrada servicioMock = mock(ServicioEntrada.class);
        Model modelMock = mock(Model.class);

        ControladorEntrada controlador = new ControladorEntrada(servicioMock);

        String vista = controlador.mostrarFormularioParaAgregarEntrada(modelMock);

        verify(modelMock).addAttribute(eq("entrada"), any(Entrada.class));

        assertEquals("crear-entrada", vista);
    }

}
