package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorEntradaTest {

    //DECLARAR MOCK 
    /*
     * DECLARAR BEFORE EACH
     * PUBLIC VOID INIT(){
     * CARGAR AL MOCK LOS DATOS DE CADAD TEST PARA NO REPETIR CODIGO
     * }
     */

     private ControladorEntrada controladorEntrada;
     private ServicioEntrada servicioEntradaMock;
     private Model modelMock;
     private List<Entrada> entradasSimuladas;
     
     @BeforeEach
     public void init(){

        servicioEntradaMock = mock(ServicioEntrada.class);
        modelMock = mock(Model.class);

        controladorEntrada = new ControladorEntrada(servicioEntradaMock);

        entradasSimuladas = new ArrayList<>();

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario(LocalTime.parse("21:00"));
        entrada1.setSeccion("Campo");
        
        entradasSimuladas.add(entrada1);

     }
    
    @Test
    public void debeMostrarMisEntradasYMostrarLaVistaCorrecta(){

        //PREPARACION
        when(servicioEntradaMock.obtenerTodasMisEntradas()).thenReturn(entradasSimuladas);

        String vistaRetornada = controladorEntrada.mostrarMisEntradas(modelMock);

        // VALIDACION

        verify(servicioEntradaMock).obtenerTodasMisEntradas();
        verify(modelMock).addAttribute("entradas", entradasSimuladas);

        assertEquals("vista-entradas-recitales", vistaRetornada);

        //Los return siempre tienen que devolver una vista "Probar con ModelAndView" o quedarse en Model

    }

    @Test
    public void debePoderAgregarUnaEntradaYRedirigaAVistaDeLasEntradas(){
        
        // PREPARACION
        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setNombreRecital("Korn");
        entrada2.setLugar("Parque Sarmiento");
        entrada2.setFecha(LocalDate.parse("2026-04-05"));
        entrada2.setHorario(LocalTime.parse("21:00"));
        entrada2.setSeccion("Campo");

        // Simulamos que el servicio creará la entrada y la devolverá
       //doNithing() Usar para cuando un metodo es void y no hace nada
        doNothing().when(servicioEntradaMock).crearEntrada(entrada2);

        //EJECUCION
        String vistaDevuelta = controladorEntrada.agregarEntrada(entrada2);

        //VALIDACION
        verify(servicioEntradaMock).crearEntrada(entrada2);

        assertEquals("redirect:/vista-entradas-recitales", vistaDevuelta);

    }

    @Test
    public void debePoderEliminarUnaEntradaPorLaIdEnLaMismaVista(){
        //PREPARACION
        Long entradaIdAEliminar = 2L;

        doNothing().when(servicioEntradaMock).eliminarEntrada(entradaIdAEliminar);
        //EJECUCION
        String vistDevuelta = controladorEntrada.eliminarEntrada(entradaIdAEliminar);
        //VALIDACION
        verify(servicioEntradaMock).eliminarEntrada(entradaIdAEliminar);

        assertEquals("redirect:/vista-entradas-recitales", vistDevuelta);
        
    }

    @Test
    public void debePoderMostrarVistaDelFormularioParaCrearUnaEntrada(){

        String vista = controladorEntrada.mostrarFormularioParaAgregarEntrada(modelMock);

        verify(modelMock).addAttribute(eq("entrada"), any(Entrada.class));

        assertEquals("crear-entrada", vista);
    }

}
