package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorPerfil {

    public ServicioPerfil servicioPerfil;

    @Autowired
    public ControladorPerfil(ServicioPerfil servicioPerfil) {
        this.servicioPerfil = servicioPerfil;
    }

    @RequestMapping( "/perfil")
    public ModelAndView irAPerfil() {
        return new ModelAndView("perfil");
    }
    @RequestMapping( "/editar-preferencias")
    public ModelAndView irAEditar() {
        return new ModelAndView("editar-preferencias");
    }
    @PostMapping("/guardar-preferencias")
    public ModelAndView guardarPreferencias(@RequestParam("generos") List<String> generosSeleccionados, @RequestParam("artistas") List<String> artistasSeleccionados){
        ModelMap model = new ModelMap();
        model.addAttribute("generos", generosSeleccionados);
        model.addAttribute("artistas", artistasSeleccionados);
        return new ModelAndView("perfil", model);
    }
}