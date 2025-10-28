package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;


import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

@Controller
public class ControladorInsignia {

    private ServicioInsignia servicio;

    @Autowired
    public ControladorInsignia(ServicioInsignia servicioMock) {
        this.servicio = servicioMock;

    }

    @GetMapping("/insignias")
    public String mostrarInsignias(Model model, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (usuario == null) {
        return "redirect:/login";
    }

    List<Insignia> insignias = servicio.obtenerInsigniasDeUsuario(usuario);
    model.addAttribute("insignias", insignias);

    return "insignias";
    }
}
