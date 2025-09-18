/*package com.tallerwebi.TDD;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioInsigniaBase;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TDD {

    private RepositorioInsigniaBase repositorioInsignia;
    private RepositorioUsuario repositorioUsuario;
    private List<UsuarioInsignia> usuarioInsigniasGuardadas; 
    private ServicioInsigniaImpl servicioInsignia;

    @BeforeEach
    public void init() {
        repositorioInsignia = mock(RepositorioInsigniaBase.class);
        repositorioUsuario = mock(RepositorioUsuario.class);

        usuarioInsigniasGuardadas = new ArrayList<>();
        RepositorioUsuarioInsignia repositorioUsuarioInsignia = new RepositorioUsuarioInsignia() {
            public void guardar(UsuarioInsignia usuarioInsignia) {
                usuarioInsigniasGuardadas.add(usuarioInsignia);
            }

            public void modificar(UsuarioInsignia usuarioInsignia) {}
            public UsuarioInsignia buscar(Long id) { return null; }

            public List<UsuarioInsignia> listarPorUsuario(Long usuarioId) {
                List<UsuarioInsignia> result = new ArrayList<>();
                for (UsuarioInsignia ui : usuarioInsigniasGuardadas) {
                    if (ui.getUsuario().getId().equals(usuarioId)) {
                        result.add(ui);
                    }
                }
                return result;
            }
        };

        servicioInsignia = new ServicioInsigniaImpl(repositorioInsignia, repositorioUsuarioInsignia, repositorioUsuario);
    }

    @Test
    public void dadoQueElUsuarioNoTieneInsigniaCuandoSeAsignaSeGuarda() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Insignia insignia = new Insignia();
        insignia.setId(10L);

        when(repositorioUsuario.buscar("1")).thenReturn(usuario);
        when(repositorioInsignia.buscar(10L)).thenReturn(insignia);

        servicioInsignia.asignarInsignia(1L, 10L);

        assertThat(usuarioInsigniasGuardadas.size(), is(1));
        assertThat(usuarioInsigniasGuardadas.get(0).getUsuario().getId(), is(1L));
        assertThat(usuarioInsigniasGuardadas.get(0).getInsignia().getId(), is(10L));
    }

    @Test
    public void dadoQueElUsuarioYaTieneInsigniaNoSeAsignaOtra() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Insignia insignia = new Insignia();
        insignia.setId(10L);

        UsuarioInsignia usuarioInsignia = new UsuarioInsignia();
        usuarioInsignia.setUsuario(usuario);
        usuarioInsignia.setInsignia(insignia);
        usuarioInsignia.setFechaObtenida(LocalDate.now());
        usuarioInsigniasGuardadas.add(usuarioInsignia);

        when(repositorioUsuario.buscar("1")).thenReturn(usuario);
        when(repositorioInsignia.buscar(10L)).thenReturn(insignia);

        servicioInsignia.asignarInsignia(1L, 10L);

        assertThat(usuarioInsigniasGuardadas.size(), is(1));
        assertThat(usuarioInsigniasGuardadas.get(0).getInsignia().getId(), is(10L));
    }
}
*/