package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


public class ControladorPerfilTest {

    ServicioPerfil servicioPerfilMock = mock(ServicioPerfil.class);
    ControladorPerfil controladorPerfil = new ControladorPerfil(servicioPerfilMock);


    @Test
    public void irAPerfilDeberiaDevolverLaVistaPerfil() {
        // 1. Ejecución
        ModelAndView modelAndView = controladorPerfil.irAPerfil();

        // 2. Verificación
        assertEquals("perfil", modelAndView.getViewName());

    }

}
