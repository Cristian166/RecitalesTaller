package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;


public class ControladorPizzas {

    private List<PizzaDTO> pizzas;

    public ControladorPizzas() {
        this.pizzas = new ArrayList<>();
    }

    public ModelAndView mostrarPizzas() {

        ModelMap model = new ModelMap();
        model.put("pizzas", this.pizzas);
        model.put("error", "No hay pizzas");
        return new ModelAndView("pizzas", model);
    }

}