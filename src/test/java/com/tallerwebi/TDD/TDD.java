package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TDD {

    private RepositorioInsigniaBase repositorioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    private RepositorioUsuario repositorioUsuario;
    private ServicioInsigniaImpl servicioInsignia;

    private List<UsuarioInsignia> guardados;

    @BeforeEach
    public void init() {
        repositorioInsignia = mock(RepositorioInsigniaBase.class);
        repositorioUsuario = mock(RepositorioUsuario.class);

        guardados = new ArrayList<>();
        repositorioUsuarioInsignia = new RepositorioUsuarioInsignia() {
            @Override
            public void guardar(UsuarioInsignia usuarioInsignia) {
                guardados.add(usuarioInsignia);
            }

            @Override
            public void modificar(UsuarioInsignia usuarioInsignia) {
                
            }

            @Override
            public UsuarioInsignia buscar(Long id) { return null; }

            @Override
            public List<UsuarioInsignia> listarPorUsuario(Long usuarioId) {
                List<UsuarioInsignia> resultado = new ArrayList<>();
                for (UsuarioInsignia ui : guardados) {
                    if (ui.getUsuario() != null && ui.getUsuario().getId() != null
                            && ui.getUsuario().getId().equals(usuarioId)) {
                        resultado.add(ui);
                    }
                }
                return resultado;
            }
        };

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

        servicioInsignia.asignarInsignia(1L, 10L);

        assertThat(guardados, hasSize(1));
        assertThat(guardados.get(0).getUsuario().getId(), is(1L));
        assertThat(guardados.get(0).getInsignia().getId(), is(10L));
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
        guardados.add(usuarioInsignia);

        when(repositorioUsuario.buscar("1")).thenReturn(usuario);
        when(repositorioInsignia.buscar(10L)).thenReturn(insignia);

        servicioInsignia.asignarInsignia(1L, 10L);

        assertThat(guardados, hasSize(1));
        assertThat(guardados.get(0).getInsignia().getId(), is(10L));
    }
}
