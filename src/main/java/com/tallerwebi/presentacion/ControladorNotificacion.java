package com.tallerwebi.presentacion;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.entidades.Usuario;

@Controller
public class ControladorNotificacion {

    private final ServicioNotificacion servicioNotificaciones;

    @Autowired
    public ControladorNotificacion(ServicioNotificacion servicioNotificaciones) {
        this.servicioNotificaciones = servicioNotificaciones;
    }
    

    @GetMapping("/notificaciones")
    public String mostrarNotificaciones(Model model,HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }


        model.addAttribute("usuario", usuario);
        model.addAttribute("notificaciones", servicioNotificaciones.obtenerNotificacionesPorUsuario(usuario));

        return "notificaciones";
    }

    @PostMapping("/notificaciones/marcar-leidas")
    public String marcarTodasComoLeidas(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            servicioNotificaciones.marcarTodasComoLeidas(usuario);
        }
        return "redirect:/notificaciones";
    }

}
