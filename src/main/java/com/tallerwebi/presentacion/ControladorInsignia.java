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
        /*Usuario usuario = new Usuario();
        usuario.setId(1L);

        Insignia i1 = new Insignia();
        i1.setId(1L);
        i1.setNombre("Participante");
        i1.setDescripcion("Participaste en tu primer recital");
        i1.setImagen("/");

        Insignia i2 = new Insignia();
        i2.setId(2L);
        i2.setNombre("Activo");
        i2.setDescripcion("Publicaste 10 reseñas");
        i2.setImagen("/");

        servicio.asignarInsignia(usuario, i1);
        servicio.asignarInsignia(usuario, i2);*/

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
