package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;

import javax.servlet.http.HttpSession;
import java.util.List;

@ControllerAdvice
public class GlobalAttributesController  {

    @Autowired
    private ServicioNotificacion servicioNotificacion;

    @ModelAttribute("notificaciones")
    public List<Notificacion> cargarNotificaciones(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return null;
        }

        return servicioNotificacion.obtenerNotificacionesPorUsuario(usuario);
    }
}