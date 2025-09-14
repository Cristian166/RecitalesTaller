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
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia; // NO mockeado, impl en memoria
    private RepositorioUsuario repositorioUsuario;
    private ServicioInsigniaImpl servicioInsignia;

    // lista en memoria para ver qué se guardó
    private List<UsuarioInsignia> guardados;

    @BeforeEach
    public void init() {
        // mockeamos solo estos dos (usando ONLY when().thenReturn())
        repositorioInsignia = mock(RepositorioInsigniaBase.class);
        repositorioUsuario = mock(RepositorioUsuario.class);

        // implementación simple en memoria para RepositorioUsuarioInsignia
        guardados = new ArrayList<>();
        repositorioUsuarioInsignia = new RepositorioUsuarioInsignia() {
            @Override
            public void guardar(UsuarioInsignia usuarioInsignia) {
                guardados.add(usuarioInsignia);
            }

            @Override
            public void modificar(UsuarioInsignia usuarioInsignia) { /* no usado en tests */ }

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

        // usamos only when().thenReturn() sobre los mocks
        when(repositorioUsuario.buscar("1")).thenReturn(usuario);
        when(repositorioInsignia.buscar(10L)).thenReturn(insignia);

        // inicialmente guardados está vacío, entonces listarPorUsuario(1L) devuelve vacío
        servicioInsignia.asignarInsignia(1L, 10L);

        // ahora comprobamos con assertThat
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

        // pre-populamos la lista como si el usuario ya tuviera la insignia
        UsuarioInsignia usuarioInsignia = new UsuarioInsignia();
        usuarioInsignia.setUsuario(usuario);
        usuarioInsignia.setInsignia(insignia);
        usuarioInsignia.setFechaObtenida(LocalDate.now());
        guardados.add(usuarioInsignia); // ahora listarPorUsuario(1L) devolverá esta entrada

        when(repositorioUsuario.buscar("1")).thenReturn(usuario);
        when(repositorioInsignia.buscar(10L)).thenReturn(insignia);

        servicioInsignia.asignarInsignia(1L, 10L);

        // como ya la tenía, no se agregó nada nuevo (sigue 1)
        assertThat(guardados, hasSize(1));
        assertThat(guardados.get(0).getInsignia().getId(), is(10L));
    }
}
