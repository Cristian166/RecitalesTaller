package com.tallerwebi.TDD;

import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.Insignia;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioInsigniaImpl;
import static org.hamcrest.core.IsEqual.equalTo;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ServicioInsigniaTest {

    @Test
    public void testAsignarInsignia() {
        ServicioInsigniaImpl servicio = new ServicioInsigniaImpl();
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("prueba@hotmail.com");

        Insignia insignia = new Insignia();
        insignia.setId(1L);
        insignia.setNombre("Participante");

        servicio.asignarInsignia(usuario, insignia);

        List<Insignia> insignias = servicio.obtenerInsigniasDeUsuario(usuario);
        assertThat(insignias.size(), equalTo(1));
        assertThat(insignias.get(0).getNombre(), equalTo("Participante"));
    }

    @Test
    public void testObtenerInsigniasDeUsuario() {
        ServicioInsigniaImpl servicio = new ServicioInsigniaImpl();
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setEmail("otro@gmail.com");

        Insignia i1 = new Insignia();
        i1.setId(1L);
        i1.setNombre("Participante");

        Insignia i2 = new Insignia();
        i2.setId(2L);
        i2.setNombre("Activo");

        servicio.asignarInsignia(usuario, i1);
        servicio.asignarInsignia(usuario, i2);

        List<Insignia> insignias = servicio.obtenerInsigniasDeUsuario(usuario);
        List<String> nombres = new ArrayList<>();
            for (Insignia i : insignias) {
                nombres.add(i.getNombre());
            }

        assertThat(insignias.size(), equalTo(2));
        assertTrue(nombres.contains("Participante"));
        assertTrue(nombres.contains("Activo"));
    }
}
