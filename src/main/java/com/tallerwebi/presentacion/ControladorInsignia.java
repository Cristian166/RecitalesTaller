package com.tallerwebi.presentacion;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Insignia;
import com.tallerwebi.dominio.ServicioInsignia;

public class ControladorInsignia {

    ServicioInsignia servicioInsignia;

    ControladorInsignia(ServicioInsignia servicioInsignia){
        this.servicioInsignia = servicioInsignia;
    }

    @RequestMapping(path = "/insignias")
    public ModelAndView verInsignias(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");

        List<Insignia> insignias = servicioInsignia.obtenerInsigniasDeUsuario(usuarioId);
        model.put("insignias", insignias);

        return new ModelAndView("insignias", model);
    }

    @RequestMapping(path = "/asignar-insignia", method = RequestMethod.POST)
    public ModelAndView asignarInsignia(@ModelAttribute("usuarioId") Long usuarioId, @ModelAttribute("insigniaId") Long insigniaId, ModelMap model) {
        try {
            servicioInsignia.asignarInsignia(usuarioId, insigniaId);
            model.put("mensaje", "Insignia asignada correctamente!");
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }

        return new ModelAndView("redirect:/insignias", model);
    }
}
