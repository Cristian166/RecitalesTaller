package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;

public class ControladorComunidadTest {

    private ControladorComunidad controladorComunidad;
    private ServicioComunidad servicioComunidadMock;
    private ServicioPublicacion servicioPublicacionMock;
    private Model modelMock;
    private Set<Comunidad> comunidades;
    private Comunidad comunidadExistente;

    @BeforeEach
    public void init(){
        servicioComunidadMock = mock(ServicioComunidad.class);
        servicioPublicacionMock = mock(ServicioPublicacion.class);
        modelMock = mock(Model.class);

        controladorComunidad = new ControladorComunidad(servicioComunidadMock, servicioPublicacionMock);

        comunidades = new HashSet<>();
        Comunidad comunidad1 = new Comunidad();
        comunidad1.setId(1L);
        comunidad1.setNombre("Comunidad de prueba");
        comunidad1.setDescripcion("Prueba");
        comunidad1.setIdioma("Español");
        comunidad1.setPaisOrigen("Argentina");

        comunidades.add(comunidad1);

        when(servicioComunidadMock.listarTodasLasComunidades()).thenReturn(comunidades);

        comunidadExistente = new Comunidad();
        comunidadExistente.setId(1L);
        comunidadExistente.setNombre("Comunidad de prueba");
        comunidadExistente.setDescripcion("Prueba");
        comunidadExistente.setIdioma("Español");

        when(servicioComunidadMock.obtenerComunidad(1L)).thenReturn(comunidadExistente);
    }


    @Test
    public void debeMostrarMisComunidades(){
        String vista = controladorComunidad.mostrarComunidades(modelMock);

        verify(servicioComunidadMock).listarTodasLasComunidades();

        verify(modelMock).addAttribute("comunidades", comunidades);

        assertEquals("comunidades", vista);
    }

    
    @Test
    public void debeRedirigirmeAUnaComunidad(){
        String vista = controladorComunidad.verComunidad(1L, modelMock);

        verify(modelMock).addAttribute("comunidad", comunidadExistente);

        assertEquals("comunidad", vista);
    }

}