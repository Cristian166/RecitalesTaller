package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioEncuesta;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.PublicacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorComunidad {
    private final ServicioComunidad servicioComunidad;
    private final ServicioPublicacion servicioPublicacion;

    @Autowired
    private HttpSession session;
    @Autowired
    private ServicioEncuesta servicioEncuesta;

    @Autowired
    public ControladorComunidad(ServicioComunidad servicioComunidad, ServicioPublicacion servicioPublicacion) {
        this.servicioComunidad = servicioComunidad;
        this.servicioPublicacion = servicioPublicacion;
    }

    // mostrar comunidades
    @GetMapping("/comunidades")
    public String mostrarComunidades(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuario);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("comunidadesUnidas", servicioComunidad.listarComunidadesUnidas(usuario.getId()));
        model.addAttribute("comunidadesSugeridas", servicioComunidad.listarComunidadesSugeridas(usuario.getId()));

        return "comunidades";
    }

    @GetMapping("/comunidad/{id}")
    public String verComunidad(@PathVariable Long id, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Comunidad comunidad = servicioComunidad.obtenerComunidad(id);

        if (comunidad == null) {
            return "redirect:/comunidades";
        }

        List<PublicacionDTO> publicacionesDTO = servicioPublicacion.listarPublicacionesDTOPorComunidad(id);

        // Obtener encuesta activa
        Encuesta encuestaActiva = servicioEncuesta.obtenerEncuestaActiva(id);

        int totalVotos = 0;

        if (encuestaActiva != null && encuestaActiva.getOpciones() != null) {
            totalVotos = encuestaActiva.getOpciones()
                    .stream()
                    .mapToInt(o -> o.getVotos())
                    .sum();
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("comunidad", comunidad);
        model.addAttribute("publicaciones", publicacionesDTO);
        model.addAttribute("nuevaPublicacion", new Publicacion());
        model.addAttribute("encuesta", encuestaActiva);
        model.addAttribute("totalVotos", totalVotos);

        return "comunidad";
    }

    @GetMapping("/comunidad/{id}/unirse")
    public String ingresarAUnaComunidad(@PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioComunidad.unirseAComunidad(usuario, id);
        }
        return "redirect:/comunidad/" + id;
    }

    @PostMapping("/comunidad/{id}/abandonar")
    public String abandonarUnaComunidad(@PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioComunidad.abandonarComunidad(usuario, id);
        }
        return "redirect:/comunidades";
    }

    @PostMapping("/comunidad/{id}/eliminar")
    public String eliminarUnaComunidad(@PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioComunidad.eliminarComunidad(id);
        }
        return "redirect:/comunidades";
    }

    @GetMapping("/crear-comunidad")
    public String mostrarFormularioCrearComunidad(Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && usuario.getEsPremium()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("nuevaComunidad", new Comunidad());
            return "crear-comunidad";
        } else {
            return "redirect:/comunidades";
        }
    }

    @PostMapping("/comunidades/crear")
    public String crearUnaComunidad(@ModelAttribute Comunidad comunidad) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && usuario.getEsPremium()) {
            comunidad.setUsuarioCreador(usuario);
            comunidad.getUsuarios().add(usuario);
            servicioComunidad.crearComunidad(comunidad, usuario);
            return "redirect:/comunidades";
        }
        return "redirect:/comunidades";
    }
}
