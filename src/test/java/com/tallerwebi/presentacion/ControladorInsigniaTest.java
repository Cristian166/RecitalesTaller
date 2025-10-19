package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.serviciosimpl.ServicioInsigniaImpl;
import com.tallerwebi.dominio.ServicioInsignia;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ControladorInsigniaTest {

    @Test
    public void mostrarInsigniasDeberiaUsarElServicioYDevolverLaVistaCorrecta() {

        ServicioInsignia servicioMock = mock(ServicioInsigniaImpl.class);

        ControladorInsignia controller = new ControladorInsignia(servicioMock);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Insignia i1 = new Insignia();
        i1.setNombre("Participante");

        Insignia i2 = new Insignia();
        i2.setNombre("Activo");

        List<Insignia> listaSimulada = new ArrayList<>();
        listaSimulada.add(i1);
        listaSimulada.add(i2);

        when(servicioMock.obtenerInsigniasDeUsuario(any(Usuario.class))).thenReturn(listaSimulada);

        List<Insignia> resultadoDelServicio = servicioMock.obtenerInsigniasDeUsuario(usuario);

        assertThat(resultadoDelServicio.size(), equalTo(2));
        assertThat(resultadoDelServicio.get(0).getNombre(), equalTo("Participante"));
        assertThat(resultadoDelServicio.get(1).getNombre(), equalTo("Activo"));

        Model modelMock = mock(Model.class);

        String vista = controller.mostrarInsignias(modelMock);

        assertThat(vista, equalTo("insignias"));
    }
}
