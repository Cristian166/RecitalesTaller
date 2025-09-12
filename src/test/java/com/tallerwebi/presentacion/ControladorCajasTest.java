package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioCaja;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorCajasTest {

    @Test
    public void dadoQueSePuedenConsultarCajasCuandoLasConsultoSinHaberAgregadoObtengoUnMensajeNoHayCajas() {

        ServicioCaja servicioCaja = mock(ServicioCaja.class);

        ControladorCajas controladorCajas = new ControladorCajas(servicioCaja);

        List<CajaDTO> cajasDTO = new ArrayList<>();
        when(servicioCaja.obtener()).thenReturn(cajasDTO);

        ModelAndView modelAndView = controladorCajas.mostrarCajas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("cajas"));

        List<CajaDTO> cajasDtoObtenidas = (List<CajaDTO>) modelAndView.getModel().get("cajas");
        assertThat(cajasDtoObtenidas.size(), equalTo(0));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay cajas"));
    }

    @Test
    public void dadoQueExistenCajasCuandoLasConsultoSeMuestran3Cajas() {

        ServicioCaja servicioCaja = mock(ServicioCaja.class);

        ControladorCajas controladorCajas = new ControladorCajas(servicioCaja);

        ModelAndView modelAndView = controladorCajas.mostrarCajas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("cajas"));

        List<CajaDTO> cajasDTO = (List<CajaDTO>) modelAndView.getModel().get("cajas");
        assertThat(cajasDTO.size(), equalTo(3));
    }

}