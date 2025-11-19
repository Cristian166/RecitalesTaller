package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioComunidadImpl;
import com.tallerwebi.infraestructura.RepositorioComunidad;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

public class ServicioComunidadImplTest {
    
    @Mock
    private RepositorioComunidad repositorioComunidad;  // Mock del repositorio

    @Mock
    private RepositorioInsignia repositorioInsignia;
    
    @Mock
    private ServicioInsignia servicioInsignia;
    
    @Mock
    private RepositorioUsuario repositorioUsuario;
    
    @Mock
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;

    @Mock
    private Usuario usuario;

    @Mock
    private Comunidad comunidad;

    @Mock
    private Comunidad comunidadSugerida;
    
    //ComunidadUnida, usuariosMock,usuarioAUnirse es para test de unirse
    
    @Mock
    private Usuario usuarioAUnirse;

    @Mock
    private Comunidad comunidadUnida;

    @Mock
    private HashSet<Usuario> usuariosMock;

    //aca termina 
    @InjectMocks
    private ServicioComunidadImpl servicioComunidad;

    @BeforeEach
    public void init() {
        
        MockitoAnnotations.openMocks(this);

        comunidad = new Comunidad();
        comunidad.setId(1L);
        comunidad.setNombre("Comunidad de prueba");
        comunidad.setDescripcion("Prueba");
        comunidad.setIdioma("Español");
        comunidad.setPaisOrigen("Argentina");

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Nahuel");

        comunidadSugerida = new Comunidad();
        comunidadSugerida = new Comunidad();
        comunidadSugerida.setId(1L);
        comunidadSugerida.setNombre("Comunidad de prueba sugerida");
        comunidadSugerida.setDescripcion("Prueba sugerida ");
        comunidadSugerida.setIdioma("Español");
        comunidadSugerida.setPaisOrigen("Argentina");

        when(repositorioComunidad.obtenerComunidadesUnidas(usuario.getId())).thenReturn(new HashSet<>(Set.of(comunidad)));
        when(repositorioComunidad.obtenerComunidadesUnidas(2L)).thenReturn(new HashSet<>());

        when(repositorioComunidad.obtenerComunidadesSugeridas(usuario.getId())).thenReturn(new HashSet<>(Set.of(comunidadSugerida)));
        when(repositorioComunidad.obtenerComunidadesSugeridas(2L)).thenReturn(new HashSet<>());
    }

    @Test
    public void debeListarComunidadesUnidas(){
        Set<Comunidad> comunidadesUnidas = servicioComunidad.listarComunidadesUnidas(usuario.getId());

        verify(repositorioComunidad).obtenerComunidadesUnidas(usuario.getId());

        assertNotNull(comunidadesUnidas);
        assertEquals(1, comunidadesUnidas.size());
        assertTrue(comunidadesUnidas.contains(comunidad));
    }

    @Test
    public void debeListarComunidadesSugeridas(){
        Set<Comunidad> comunidadesSugeridas = servicioComunidad.listarComunidadesSugeridas(usuario.getId());

        verify(repositorioComunidad).obtenerComunidadesSugeridas(usuario.getId());

        assertNotNull(comunidadesSugeridas);
        assertEquals(1, comunidadesSugeridas.size());

        assertTrue(comunidadesSugeridas.contains(comunidadSugerida));
    }

    @Test
    public void debeObtenerUnaComunidadPorId(){
        Long comunidadId = 1L;
        Comunidad comunidad = new Comunidad();
        comunidad.setId(comunidadId);
        comunidad.setNombre("Comunidad de prueba");

        when(repositorioComunidad.obtenerComunidadPorId(comunidadId)).thenReturn(comunidad);

        Comunidad comunidadObtenida = servicioComunidad.obtenerComunidad(comunidadId);

        verify(repositorioComunidad).obtenerComunidadPorId(comunidadId);

        assertNotNull(comunidadObtenida);
        assertEquals(comunidadId, comunidadObtenida.getId());
        assertEquals("Comunidad de prueba", comunidadObtenida.getNombre());
    }

    @Test
    public void debePoderUnirseAUnaComunidad(){
        
        when(usuarioAUnirse.getId()).thenReturn(1L);

        when(comunidadUnida.getUsuarios()).thenReturn(usuariosMock);

        when(repositorioComunidad.obtenerComunidadPorId(1L)).thenReturn(comunidadUnida);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);

        servicioComunidad.unirseAComunidad(usuarioAUnirse, 1L);

        verify(repositorioComunidad).guardarUnaComunidad(comunidadUnida);

        verify(usuariosMock).add(captor.capture());

        assertEquals(usuarioAUnirse, captor.getValue());
    }

    @Test
    public void debePoderAbandonarLaComunidad(){
        Usuario usuarioAAbandonar = new Usuario();
        usuarioAAbandonar.setId(1L);
        usuarioAAbandonar.setNombre("Nahuel");

        Long comunidadId = 1L;
        
        // Mockear el comportamiento del repositorio, indicando que el método abandonarComunidad no hace nada
        doNothing().when(repositorioComunidad).abandonarComunidad(usuarioAAbandonar, comunidadId);

        servicioComunidad.abandonarComunidad(usuarioAAbandonar, comunidadId);

        verify(repositorioComunidad).abandonarComunidad(usuarioAAbandonar, comunidadId);
    }

    //ACA IRIA EL CREAR COMUNIDAD

    @Test
    public void debePoderEliminarUnaComunidad(){
        Long comunidadId = 1L;
        
        doNothing().when(repositorioComunidad).borrarComunidad(comunidadId);

        servicioComunidad.eliminarComunidad(comunidadId);

        verify(repositorioComunidad).borrarComunidad(comunidadId);
    }

    @Test
    public void debeContarMiembrosDeComunidad(){
        Long comunidadId = 1L;
        Long cantidadEsperada = 2L;
        
        when(repositorioComunidad.contarMiembrosDeComunidad(comunidadId)).thenReturn(cantidadEsperada);

        long cantidadMiembros = servicioComunidad.contarMiembrosComunidad(comunidadId);

        verify(repositorioComunidad).contarMiembrosDeComunidad(comunidadId);

        assertEquals(cantidadEsperada, cantidadMiembros);
    }

    @Test
    public void debeVerificarQueExistaUnaComunidadPorNombre(){
        
        String nombreComunidad = "Comunidad de prueba";
        boolean comunidadExistente = true;

        when(repositorioComunidad.obtenerComunidadPorNombre(nombreComunidad)).thenReturn(new Comunidad());

        boolean existe = servicioComunidad.existeComunidadPorNombre(nombreComunidad);

        verify(repositorioComunidad).obtenerComunidadPorNombre(nombreComunidad);
        
        assertTrue(existe);
    }

    @Test
    public void debeCrearUnaComunidadYAsignarInsigniaSiNoTiene(){

        Usuario usuario = mock(Usuario.class);
        Comunidad comunidad = new Comunidad();
        comunidad.setId(1L);
        comunidad.setNombre("Comunidad de prueba");

        when(repositorioUsuario.buscarId(usuario.getId())).thenReturn(usuario);
        when(repositorioUsuarioInsignia.existe(usuario.getId(), 7L)).thenReturn(false);
        
        Insignia insignia = mock(Insignia.class);
        when(repositorioInsignia.obtenerPorId(7L)).thenReturn(insignia);
        
        when(repositorioComunidad.guardarUnaComunidad(comunidad)).thenReturn(comunidad);

        Comunidad comunidadCreada = servicioComunidad.crearComunidad(comunidad, usuario);

        verify(servicioInsignia).asignarInsignia(usuario, insignia);

        verify(repositorioComunidad).guardarUnaComunidad(comunidad);

        assertNotNull(comunidadCreada);  // Asegurarse de que la comunidad fue creada
    }

    @Test
    public void noDebeAsignarInsigniaSiUsuarioYaLaTiene() {

        Usuario usuario = mock(Usuario.class);
        Comunidad comunidad = new Comunidad();
        comunidad.setId(1L);
        comunidad.setNombre("Comunidad de prueba");

        when(repositorioUsuario.buscarId(usuario.getId())).thenReturn(usuario);
        when(repositorioUsuarioInsignia.existe(usuario.getId(), 7L)).thenReturn(true);

        when(repositorioComunidad.guardarUnaComunidad(comunidad)).thenReturn(comunidad);

        Comunidad comunidadCreada = servicioComunidad.crearComunidad(comunidad, usuario);

        // Verificar que NO se haya asignado la insignia
        verify(servicioInsignia, never()).asignarInsignia(any(), any());

        verify(repositorioComunidad).guardarUnaComunidad(comunidad);

        assertNotNull(comunidadCreada);  // Asegurarse de que la comunidad fue creada
    }
}
