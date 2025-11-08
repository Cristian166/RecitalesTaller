package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioComunidadImpl;
import com.tallerwebi.infraestructura.RepositorioComunidad;

public class ServicioComunidadTest {
    
    @Mock
    private RepositorioComunidad repositorioComunidadMock;
    @InjectMocks
    private ServicioComunidadImpl servicioComunidad;

    private Comunidad comunidadExistente;
    private Usuario usuario;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        comunidadExistente = new Comunidad();
        comunidadExistente.setId(1L);
        comunidadExistente.setNombre("Comunidad de prueba");
        comunidadExistente.setDescripcion("Prueba");
        comunidadExistente.setIdioma("Español");
        comunidadExistente.setPaisOrigen("Argentina");

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");

        when(repositorioComunidadMock.obtenerPorId(1L)).thenReturn(comunidadExistente);
        when(repositorioComunidadMock.obtenerPorId(2L)).thenReturn(null);  // Comunidad no existe
        when(repositorioComunidadMock.obtenerMisComunidades()).thenReturn(new HashSet<>(Set.of(comunidadExistente)));

    }

    @Test
    public void debeListarTodasLasComunidades(){
        Set<Comunidad> comunidades = servicioComunidad.listarTodasLasComunidades();

        verify(repositorioComunidadMock).obtenerMisComunidades();

        assertNotNull(comunidades);
        assertEquals(1, comunidades.size());
        assertTrue(comunidades.contains(comunidadExistente));
    }

    @Test
    public void debeOtenerUnaComunidad(){
        Comunidad comunidad = servicioComunidad.obtenerComunidad(1L);

        verify(repositorioComunidadMock).obtenerPorId(1L);

        assertNotNull(comunidad);
        assertEquals("Comunidad de prueba", comunidad.getNombre());
    }
}