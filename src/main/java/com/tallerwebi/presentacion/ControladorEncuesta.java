package com.tallerwebi.presentacion;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioEncuesta;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

@Controller
public class ControladorEncuesta {

    private ServicioEncuesta servicioEncuesta;
    private ServicioComunidad servicioComunidad;
    private HttpSession session;

    @Autowired
    public ControladorEncuesta( ServicioEncuesta servicioEncuesta,
            ServicioComunidad servicioComunidad,
            HttpSession session) {
        this.servicioEncuesta = servicioEncuesta;
        this.servicioComunidad = servicioComunidad;
        this.session = session;
    }

    @GetMapping("/comunidad/{id}/encuesta/crear")
    public String mostrarCrearEncuesta(@PathVariable Long id, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Comunidad comunidad = servicioComunidad.obtenerComunidad(id);

        if (!comunidad.getUsuarioCreador().getId().equals(usuario.getId())) {
            return "redirect:/comunidad/" + id;
        }

        model.addAttribute("comunidad", comunidad);
        return "crear-encuesta";
    }

    @PostMapping("/comunidad/{id}/encuesta/crear")
    public String crearEncuesta(
            @PathVariable Long id,
            @RequestParam String pregunta,
            @RequestParam List<String> opciones) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Comunidad comunidad = servicioComunidad.obtenerComunidad(id);

        servicioEncuesta.crearEncuesta(comunidad, usuario, pregunta, opciones);

        return "redirect:/comunidad/" + id;
    }

    @PostMapping("/encuesta/votar")
    public String votar(@RequestParam Long opcionId, @RequestParam Long comunidadId) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        servicioEncuesta.votar(opcionId, usuario);

        return "redirect:/comunidad/" + comunidadId;
    }

    @PostMapping("/encuesta/{id}/eliminar")
    public String eliminarEncuesta(@PathVariable Long id, @RequestParam Long comunidadId) {

        servicioEncuesta.eliminarEncuesta(id);

        return "redirect:/comunidad/" + comunidadId;
    }

}
