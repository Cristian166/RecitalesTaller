package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.entidades.Preferencia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioPerfilImpl;
import com.tallerwebi.infraestructura.RepositorioPerfil;

public class ServicioPerfilImplTest {

    private ServicioPerfilImpl servicioPerfil;
    private RepositorioPerfil repositorioPerfilMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        repositorioPerfilMock = mock(RepositorioPerfil.class);
        servicioPerfil = new ServicioPerfilImpl(repositorioPerfilMock);
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getId()).thenReturn(1L);
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
}