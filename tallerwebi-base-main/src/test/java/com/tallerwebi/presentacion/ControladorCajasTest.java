package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class ControladorCajasTest {

    @Test
    public void dadoQueSePuedenConsultarCajasCuandoLasConsultoSinHaberAgregadoObtengoUnMensajeNoHayCajas() {

        ControladorCajas controladorCajas = new ControladorCajas();

        ModelAndView modelAndView = controladorCajas.mostrarCajas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("cajas"));

        List<CajaDTO> cajasDTO = (List<CajaDTO>) modelAndView.getModel().get("cajas");
        assertThat(cajasDTO.size(), equalTo(0));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay cajas"));
    }

    @Test
    public void dadoQueExistenCajasCuandoLasConsultoSeMuestran3Cajas() {

        ControladorCajas controladorCajas = new ControladorCajas();

        ModelAndView modelAndView = controladorCajas.mostrarCajas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("cajas"));

        List<CajaDTO> cajasDTO = (List<CajaDTO>) modelAndView.getModel().get("cajas");
        assertThat(cajasDTO.size(), equalTo(3));
        assertThat(modelAndView.getModel().get("error"), equalTo(null));
    }

}