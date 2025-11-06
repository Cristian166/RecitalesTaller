package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.EntradaDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
        Model modelMock = mock(Model.class);

        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        EntradaDTO entrada1 = new EntradaDTO();

        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");

        EntradaDTO entrada2 = new EntradaDTO();

        entrada2.setNombreRecital("Dark Tranquility");
        entrada2.setLugar("El teatrito");
        entrada2.setFecha(LocalDate.parse("2026-01-15"));
        entrada2.setHorario("21:00");
        entrada2.setSeccion("Campo");


        List<EntradaDTO> entradasSimuladas = new ArrayList<>();
        entradasSimuladas.add(entrada1);
        entradasSimuladas.add(entrada2);

        when( servicioMock.obtenerEntradasPorUsuario(usuario)).thenReturn(entradasSimuladas);

        String vistaRetornada = controlador.mostrarMisEntradas(null, modelMock, sessionMock);

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
        Long id = invocation.getArgument(0);
        entradasSimuladas.removeIf(entrada -> entrada.getId().equals(id)); 
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

    @Test
    public void debePoderValidarUnaEntradaYaCreada(){
        ServicioEntrada servicioMock = mock(ServicioEntrada.class);
        HttpSession sessionMock = mock(HttpSession.class);
        ControladorEntrada controlador = new ControladorEntrada(servicioMock);

        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setValidada(false);
    
        when(servicioMock.buscarPorId(1L)).thenReturn(entrada1);
        
        doAnswer(invocation -> {
        entrada1.setValidada(true);
        return null;
        }).when(servicioMock).validarEntrada(entrada1.getId(), usuario);


        ModelAndView vistaDevuelta = controlador.validarEntrada(entrada1.getId(), sessionMock);

        assertEquals("validar-entrada", vistaDevuelta.getViewName());
        assertEquals("La entrada fue validada correctamente.", vistaDevuelta.getModel().get("mensajeExito"));
        assertTrue(entrada1.getValidada());
        verify(servicioMock).validarEntrada(entrada1.getId(), usuario);

    }

    @Test
    public void debeDarErrorSiIngresamosIdInexistenteAlValidarEntrada() {
        ServicioEntrada servicioMock = mock(ServicioEntrada.class);
        HttpSession sessionMock = mock(HttpSession.class);
        ControladorEntrada controlador = new ControladorEntrada(servicioMock);

        when(servicioMock.buscarPorId(999L)).thenReturn(null);

        ModelAndView vistaDevuelta = controlador.validarEntrada(999L, sessionMock);

        assertEquals("validar-entrada", vistaDevuelta.getViewName());
        assertEquals("No se encontró la entrada con ID: 999", vistaDevuelta.getModel().get("mensajeError"));
    }

    @Test
    public void debeManejarEliminacionDeEntradaInexistente() {
        ServicioEntrada servicioMock = mock(ServicioEntrada.class);
        ControladorEntrada controlador = new ControladorEntrada(servicioMock);

        doThrow(new IllegalArgumentException("No existe la entrada"))
            .when(servicioMock).eliminarEntrada(999L);

        String vista = controlador.eliminarEntrada(999L);

        assertEquals("redirect:/vista-entradas-recitales?error", vista);
        verify(servicioMock).eliminarEntrada(999L);
    }

}
