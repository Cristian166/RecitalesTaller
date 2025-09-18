package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class ControladorCajas {

    private List<CajaDTO> cajas;
    
    public ControladorCajas() {
        this.cajas = new ArrayList<>();
    }

    public ModelAndView mostrarCajas() {

        ModelMap model = new ModelMap();
        model.put("cajas", this.cajas);
        model.put("error", "No hay cajas");
        return new ModelAndView("cajas", model);
    }
}
