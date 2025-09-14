package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioCaja;

public class ControladorCajas {

    private List<CajaDTO> cajas;
    private ServicioCaja servicioCaja;
    
    public ControladorCajas(ServicioCaja servicioCaja) {
        this.cajas = servicioCaja.obtener();
        this.servicioCaja = servicioCaja;
    }

    public ModelAndView mostrarCajas() {

        ModelMap model = new ModelMap();
        model.put("cajas", this.servicioCaja.obtener());
        model.put("error", "No hay cajas");
        return new ModelAndView("cajas", model);
    }
}
