package com.tallerwebi.dominio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import com.tallerwebi.dominio.entidades.Preferencia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioPerfilImpl;
import com.tallerwebi.infraestructura.RepositorioPerfil;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

public class ServicioPerfilImplTest {

    private ServicioPerfilImpl servicioPerfil;
    private RepositorioPerfil repositorioPerfilMock;
    private ServicioInsignia servicioInsigniaMock;
    private RepositorioInsignia repositorioInsigniaMock;
    private RepositorioUsuario repositorioUsuarioMock;
    private RepositorioUsuarioInsignia repositorioUsuarioInsigniaMock;
    
    @Spy
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        repositorioPerfilMock = mock(RepositorioPerfil.class);
        servicioInsigniaMock = mock(ServicioInsignia.class);
        repositorioInsigniaMock = mock(RepositorioInsignia.class);
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        repositorioUsuarioInsigniaMock = mock(RepositorioUsuarioInsignia.class);
        
        servicioPerfil = new ServicioPerfilImpl(repositorioPerfilMock,
            servicioInsigniaMock,
            repositorioInsigniaMock,
            repositorioUsuarioMock,
            repositorioUsuarioInsigniaMock  
        );

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
  
    }

    @Test
    public void consultarPreferenciasDeberiaDevolverListaCorrecta() {
        // preparación
        Preferencia rock = new Preferencia("Rock", "Genero");
        Preferencia pop = new Preferencia("Pop", "Genero");
        List<Preferencia> preferenciasMock = Arrays.asList(rock, pop);

        when(repositorioPerfilMock.obtenerPreferenciaExistentes()).thenReturn(preferenciasMock);

        // ejecución
        List<Preferencia> resultado = servicioPerfil.consultarPreferenciaExistentes();

        // validación
        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0).getNombre(), equalTo("Rock"));
        assertThat(resultado.get(1).getNombre(), equalTo("Pop"));
        verify(repositorioPerfilMock, times(1)).obtenerPreferenciaExistentes();
    }

    @Test
    public void obtenerGenerosDeberiaRetornarListaDeGeneros() {
        // preparación
        when(repositorioPerfilMock.obtenerNombresDePreferenciasPorTipo("genero"))
                .thenReturn(Arrays.asList("Rock", "Pop", "Rap"));

        // ejecución
        List<String> generos = servicioPerfil.obtenerGeneros();

        // validación
        assertThat(generos.get(0), equalTo("Rock"));
        assertThat(generos.get(1), equalTo("Pop"));
        assertThat(generos.get(2), equalTo("Rap"));
        verify(repositorioPerfilMock, times(1)).obtenerNombresDePreferenciasPorTipo("genero");
    }

    @Test
    public void obtenerArtistasDeberiaRetornarListaDeArtistas() {
        // preparación
        when(repositorioPerfilMock.obtenerNombresDePreferenciasPorTipo("artista"))
                .thenReturn(Arrays.asList("Duki", "Emilia"));

        // ejecución
        List<String> artistas = servicioPerfil.obtenerArtistas();

        assertThat(artistas.get(0), equalTo("Duki"));
        assertThat(artistas.get(1), equalTo("Emilia"));
        verify(repositorioPerfilMock, times(1)).obtenerNombresDePreferenciasPorTipo("artista");
    }

    @Test
    public void obtenerPreferenciasPorUsuarioDeberiaLlamarAlRepositorio() {
        // preparación
        PreferenciaUsuario preferenciasMock = mock(PreferenciaUsuario.class);
        when(repositorioPerfilMock.obtenerPreferenciasPorUsuario(usuarioMock))
                .thenReturn(preferenciasMock);

        // ejecución
        PreferenciaUsuario resultado = servicioPerfil.obtenerPreferenciasPorUsuario(usuarioMock);

        // validación
        assertThat(resultado, equalTo(preferenciasMock));
        verify(repositorioPerfilMock, times(1)).obtenerPreferenciasPorUsuario(usuarioMock);
    }

    @Test
    public void guardarPreferenciasDeberiaInvocarRepositorioConLosDatosCorrectos() {
        // preparación
        List<String> generos = Arrays.asList("Rock", "Pop");
        List<String> artistas = Arrays.asList("Duki");
        List<String> regiones = Arrays.asList("Argentina");
        List<String> epocas = Arrays.asList("2000's");

        // ejecución
        servicioPerfil.guardarPreferencias(usuarioMock.getId(), generos, artistas, regiones, epocas);

        // validación
        verify(repositorioPerfilMock, times(1))
                .guardarPreferenciasPorUsuario(usuarioMock.getId(), generos, artistas, regiones, epocas);
    }

    @Test
    public void debeObtenerUsuarioPorId(){
        Usuario usuarioMock = mock(Usuario.class);
        Long idUsuario = 1L;
        when(usuarioMock.getId()).thenReturn(idUsuario);

        when(repositorioPerfilMock.obtenerUsuarioPorId(idUsuario)).thenReturn(usuarioMock);

        Usuario usuarioRecuperado = servicioPerfil.obtenerUsuarioPorId(idUsuario);

        verify(repositorioPerfilMock, times(1)).obtenerUsuarioPorId(idUsuario);
        
        assertThat(usuarioRecuperado,is(equalTo(usuarioMock)));
    }

    @Test
    public void debePoderActualizarMiInformacion(){
        Long idUsuario = 1L;
        String nuevoNombre = "Carlos";
        String nuevoApellido = "Lopez";
        String nuevoTelefono = "987654321";
        String nuevoEmail = "carlos.lopez@example.com";
        String nuevaDireccion = "Nueva Calle 789";
        String nuevoPais = "España";
        String nuevaProvincia = "Madrid";
        String nuevaImagen = "nueva-imagen.jpg";
    
        when(repositorioUsuarioMock.buscarId(idUsuario)).thenReturn(usuarioMock);

        servicioPerfil.actualizarPerfil(idUsuario, nuevoNombre, nuevoApellido, nuevoTelefono, nuevoEmail,
                nuevaDireccion, nuevoPais, nuevaProvincia, nuevaImagen);

        verify(repositorioUsuarioMock, times(1)).buscarId(idUsuario);
        verify(repositorioUsuarioMock, times(1)).modificar(usuarioMock);

        assertEquals(nuevoNombre, usuarioMock.getNombre());
        assertEquals(nuevoApellido, usuarioMock.getApellido());
        assertEquals(nuevoTelefono, usuarioMock.getTelefono());
        assertEquals(nuevoEmail, usuarioMock.getEmail());
        assertEquals(nuevaDireccion, usuarioMock.getDireccion());
        assertEquals(nuevoPais, usuarioMock.getPais());
        assertEquals(nuevaProvincia, usuarioMock.getProvincia());
        assertEquals(nuevaImagen, usuarioMock.getImagen());
    }

    @Test
    public void debeLanzarExcepcionSiUsuarioNoExisteAlActualizar(){
        Long idUsuarioInvalido = 99L;
        String nuevoNombre = "Carlos";
        String nuevoApellido = "Lopez";
        String nuevoTelefono = "987654321";
        String nuevoEmail = "carlos.lopez@example.com";
        String nuevaDireccion = "Nueva Calle 789";
        String nuevoPais = "España";
        String nuevaProvincia = "Madrid";
        String nuevaImagen = "nueva-imagen.jpg";

        when(repositorioUsuarioMock.buscarId(idUsuarioInvalido)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
        servicioPerfil.actualizarPerfil(idUsuarioInvalido, nuevoNombre, nuevoApellido, nuevoTelefono, nuevoEmail,
                nuevaDireccion, nuevoPais, nuevaProvincia, nuevaImagen);
        });

        verify(repositorioUsuarioMock, times(1)).buscarId(idUsuarioInvalido);

        verify(repositorioUsuarioMock, times(0)).modificar(any(Usuario.class));
    }
}