package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;


public class PracticandoTest {

    @Test
    public void dadoQueSePuedeConsultarPizzasCuandoLasConsultoSinHaberAgregadoObtengoUnMensajeNoHayPizzas() {

        ControladorPizzas controladorPizzas = new ControladorPizzas();

        ModelAndView modelAndView = controladorPizzas.mostrarPizzas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("pizzas"));

        List<PizzaDTO> pizzasDTO = (List<PizzaDTO>) modelAndView.getModelMap().get("pizzas");

        assertThat(pizzasDTO.size(), equalTo(0));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay pizzas"));

    }

    @Test
    public void dadoQueExistenPizzasCuandoLasConsultoObtengo3Pizzas() {

        ControladorPizzas controladorPizzas = new ControladorPizzas();

        ModelAndView modelAndView = controladorPizzas.mostrarPizzas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("pizzas"));

        List<PizzaDTO> pizzasDTO = (List<PizzaDTO>) modelAndView.getModelMap().get("pizzas");

        assertThat(pizzasDTO.size(), equalTo(3));

        //assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("No hay pizzas"));

    }
    
}
