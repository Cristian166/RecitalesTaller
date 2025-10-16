package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ControladorSuscripcion {

    @RequestMapping(path = "/confirmacion-pago-premium", method = RequestMethod.GET)
    public ModelAndView irAPagarPlanPremium() { return new ModelAndView( "confirmacion-pago-premium"); }

    @PostMapping("/confirmar-pago-premium")
    public String confirmarPagoPremium() {
        // lógica adicional
        // como cambiar el estado de premium del usuario, etc.

        // Redirigir al usuario a la página de inicio
        return "redirect:/home-Page";  // Esto redirige al home-Page
    }




}
