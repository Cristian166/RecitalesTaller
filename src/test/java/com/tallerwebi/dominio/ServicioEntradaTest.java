package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioEntradaImpl;
import com.tallerwebi.infraestructura.RepositorioEntrada;
import com.tallerwebi.infraestructura.DTOs.EntradaDTO;

public class ServicioEntradaTest {
    
    private ServicioEntradaImpl servicio;
    private RepositorioEntrada repositorioEntradaMock;

    @BeforeEach
    void setUp() {
        // Inicializa el mock de RepositorioEntrada
        repositorioEntradaMock = mock(RepositorioEntrada.class);
        servicio = new ServicioEntradaImpl(repositorioEntradaMock, null);  // Inyectamos el mock en el servicio

        List<Entrada> entradasMock = new ArrayList<>();
        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(entradasMock);

    }

    @Test
    public void debePoderCrearUnaentradaNueva(){    

        Entrada entrada1 = new Entrada();
        Usuario usuario= new Usuario();

        entrada1.setId(1L);
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");
        entrada1.setValidada(false);

        servicio.crearEntrada(entrada1, usuario);

        verify(repositorioEntradaMock).guardarEntradaPorUsuario(entrada1, usuario);

    }

    @Test
    public void  debeObtenerTodasLasEntradas(){

        Usuario usuario= new Usuario();
        
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
        entrada2.setHorario(("21:00"));
        entrada2.setSeccion("Campo");

        servicio.crearEntrada(entrada1,usuario);
        servicio.crearEntrada(entrada2,usuario);

        List<Entrada> entradasMock = new ArrayList<>();
        entradasMock.add(entrada1);
        entradasMock.add(entrada2);
        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(entradasMock);

        List<Entrada> entradas = servicio.obtenerTodasMisEntradas();

        verify(repositorioEntradaMock).obtenerEntradas(); 
        assertEquals(entradasMock.size(), entradas.size());
        assertTrue(entradas.contains(entrada1));
        assertTrue(entradas.contains(entrada2));

    }
    
    @Test
    public void debePonerEliminarUnaEntradaPorLaId(){

        Usuario usuario= new Usuario();

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);

        servicio.crearEntrada(entrada1, usuario);
        servicio.crearEntrada(entrada2, usuario);

        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(List.of(entrada1, entrada2));

        servicio.eliminarEntrada(entrada1.getId());

        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(List.of(entrada2));

        List<Entrada> entradas = servicio.obtenerTodasMisEntradas();

        assertEquals(1, entradas.size());
        assertFalse(entradas.contains(entrada1));
        assertTrue(entradas.contains(entrada2));

        verify(repositorioEntradaMock).eliminarPorId(1L);
        verify(repositorioEntradaMock, times(1)).obtenerEntradas();

    }

    @Test
    public void debePoderValidarUnaEntrada(){

        Usuario usuario= new Usuario();

        Entrada entrada1 = new Entrada();

        entrada1.setId(1L);
        entrada1.setValidada(false);

        servicio.crearEntrada(entrada1, usuario);
        
        when(repositorioEntradaMock.buscarPorId(1L)).thenReturn(entrada1);

        when(repositorioEntradaMock.obtenerEntradas()).thenReturn(List.of(entrada1));

        servicio.validarEntrada(1L, usuario, 8);
        

        assertEquals(1, servicio.obtenerTodasMisEntradas().size());
        assertTrue(entrada1.getValidada());

        verify(repositorioEntradaMock, times(2)).guardarEntradaPorUsuario(entrada1, usuario);
    }

    @Test
    public void debePoderObtenerEntradasPorId(){

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);

        when(repositorioEntradaMock.buscarPorId(1L)).thenReturn(entrada1);

        Entrada entradaEncontrada= servicio.buscarPorId(entrada1.getId());

        assertEquals(entrada1, entradaEncontrada);
        verify(repositorioEntradaMock).buscarPorId(entrada1.getId());

    }

    @Test
    public void debePoderObtenerEntradasPorUsuario(){

        Usuario usuario= new Usuario();

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);


        when(repositorioEntradaMock.obtenerEntradasPorUsuario(usuario)).thenReturn(List.of(entrada1, entrada2));
        List<EntradaDTO> entradasDeUsuario = servicio.obtenerEntradasPorUsuario(usuario);

        assertEquals(2, entradasDeUsuario.size());
        verify(repositorioEntradaMock).obtenerEntradasPorUsuario(usuario);
    }
        
}
