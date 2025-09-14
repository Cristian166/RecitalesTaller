package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TDDTest {

    private RepositorioInsigniaBase repositorioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    private RepositorioUsuario repositorioUsuario;
    private ServicioInsigniaImpl servicioInsignia;

    @BeforeEach
    public void init() {
        repositorioInsignia = mock(RepositorioInsigniaBase.class);
        repositorioUsuarioInsignia = mock(RepositorioUsuarioInsignia.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioInsignia = new ServicioInsigniaImpl(repositorioInsignia, repositorioUsuarioInsignia, repositorioUsuario);
    }

    @Test
    public void queSeAsigneInsigniaSiUsuarioNoLaTiene() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Insignia insignia = new Insignia();
        insignia.setId(10L);

        when(repositorioUsuario.buscar("1")).thenReturn(usuario);
        when(repositorioInsignia.buscar(10L)).thenReturn(insignia);
        when(repositorioUsuarioInsignia.listarPorUsuario(1L)).thenReturn(new ArrayList<>());

        servicioInsignia.asignarInsignia(1L, 10L);

        verify(repositorioUsuarioInsignia, times(1)).guardar(any(UsuarioInsignia.class));
    }

    @Test
    public void queNoSeAsigneInsigniaSiUsuarioYaLaTiene() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Insignia insignia = new Insignia();
        insignia.setId(10L);

        UsuarioInsignia usuarioInsignia = new UsuarioInsignia();
        usuarioInsignia.setUsuario(usuario);
        usuarioInsignia.setInsignia(insignia);
        usuarioInsignia.setFechaObtenida(LocalDate.now());

        List<UsuarioInsignia> existentes = List.of(usuarioInsignia);

        when(repositorioUsuario.buscar("1")).thenReturn(usuario);
        when(repositorioInsignia.buscar(10L)).thenReturn(insignia);
        when(repositorioUsuarioInsignia.listarPorUsuario(1L)).thenReturn(existentes);

        servicioInsignia.asignarInsignia(1L, 10L);

        verify(repositorioUsuarioInsignia, never()).guardar(any(UsuarioInsignia.class));
    }
}
