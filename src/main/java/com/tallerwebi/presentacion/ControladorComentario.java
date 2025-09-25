package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.ServicioComentario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorComentario {

    private final ServicioComentario servicio;

    @Autowired
    public ControladorComentario(ServicioComentario servicio) {
        this.servicio = servicio;
    }

    @RequestMapping(path = "/foro-comentarios", method = RequestMethod.GET)
    public ModelAndView mostrarComentarios() {
        ModelMap modelo = new ModelMap();
        modelo.put("comentarios", servicio.listarComentarios());
        modelo.put("comentario", new Comentario());
        return new ModelAndView("foro-comentarios", modelo);
    }

    @RequestMapping(path = "/foro-comentarios", method = RequestMethod.POST)
    public ModelAndView comentar(@ModelAttribute("comentario") Comentario comentario, HttpServletRequest request) {
        Usuario usuario = comentario.getUsuario();

        servicio.crearComentario(usuario, comentario.getTexto());

        System.out.println("Comentarios en memoria:");
    for (Comentario c : servicio.listarComentarios()) {
        System.out.println(c.getUsuario().getEmail() + ": " + c.getTexto());
    }

        ModelMap modelo = new ModelMap();
        modelo.put("comentarios", servicio.listarComentarios());
        modelo.put("comentario", new Comentario());
        return new ModelAndView("foro-comentarios", modelo);
    }
}
