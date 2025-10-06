package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Insignia;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorInsigniaTest {

    @Test
    public void mostrarInsigniasDeberiaAgregarLasInsigniasAlModelo() {

        ServicioInsignia servicioMock = mock(ServicioInsignia.class);

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

        when(servicioMock.obtenerInsigniasDeUsuario(usuario)).thenReturn(listaSimulada);

        Model modelMock = mock(Model.class);

        String vista = controller.mostrarInsignias(modelMock);

        assertThat(vista, equalTo("insignias"));
    }
}
