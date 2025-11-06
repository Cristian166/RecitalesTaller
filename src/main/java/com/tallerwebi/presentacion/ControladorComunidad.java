package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.PublicacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class ControladorComunidad {
    private final ServicioComunidad servicioComunidad;
    private final ServicioPublicacion servicioPublicacion;

    @Autowired
    private HttpSession session;

    @Autowired
    public ControladorComunidad(ServicioComunidad servicioComunidad, ServicioPublicacion servicioPublicacion) {
        this.servicioComunidad = servicioComunidad;
        this.servicioPublicacion = servicioPublicacion;
    }

    // mostrar comunidades
    @GetMapping("/comunidades")
    public String mostrarComunidades(Model model) {
        Set<Comunidad> comunidades = servicioComunidad.listarTodasLasComunidades();
        comunidades.forEach(c -> System.out.println(c.getUsuarios()));
        model.addAttribute("comunidades", comunidades);
        return "comunidades";
    }

    @GetMapping("/comunidad/{id}")
    public String verComunidad(@PathVariable Long id, Model model) {
        Comunidad comunidad = servicioComunidad.obtenerComunidad(id);

        if (comunidad == null) {
            return "redirect:/comunidades";
        }

        List<PublicacionDTO> publicacionesDTO = servicioPublicacion.listarPublicacionesDTOPorComunidad(id);

        model.addAttribute("comunidad", comunidad);
        model.addAttribute("publicaciones", publicacionesDTO);
        model.addAttribute("nuevaPublicacion", new Publicacion());
        return "comunidad";
    }

    @PostMapping("/comunidad/{id}/unirse")
    public String ingresarAUnaComunidad(@PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        servicioComunidad.unirseAComunidad(usuario, id);
        return "redirect:/comunidades/" + id;
    }

    @PostMapping("/comunidad/{id}/abandonar")
    public String abandonarUnaComunidad(@PathVariable Long id) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        servicioComunidad.abandonarComunidad(usuario, id);

        return "redirect:/comunidades";
    }

}
