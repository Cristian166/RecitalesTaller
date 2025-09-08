package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


public class PruebaController {

    @GetMapping("/prueba")
    public ModelAndView pruebaSaludo() {
        ModelMap model = new ModelMap();
        model.put("mensaje", "hola prueba");
        return new ModelAndView("prueba", model);
    }
}
