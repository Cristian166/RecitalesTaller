package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;

import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
public class ControladorPerfil {

    private final ServicioPerfil servicioPerfil;
    private ServicioInsignia servicioInsignia;

    @Autowired
    public ControladorPerfil(ServicioPerfil servicioPerfil, ServicioInsignia servicioInsignia) {
        this.servicioPerfil = servicioPerfil;
        this.servicioInsignia = servicioInsignia;
    }

    @GetMapping("/perfil")
    public ModelAndView irAPerfil(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        PreferenciaUsuario preferencias = servicioPerfil.obtenerPreferenciasPorUsuario(usuario);
        List<Insignia> insignias = servicioInsignia.obtenerInsigniasDeUsuario(usuario);

        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("apellido", usuario.getApellido());
        model.addAttribute("email", usuario.getEmail());
        model.addAttribute("insignias", insignias);

        if (preferencias != null) {
            model.addAttribute("generos", preferencias.getGenerosSeleccionados());
            model.addAttribute("artistas", preferencias.getArtistasSeleccionados());
            model.addAttribute("regiones", preferencias.getRegionesSeleccionadas());
            model.addAttribute("epocas", preferencias.getEpocasSeleccionadas());
        } else {
            model.addAttribute("generos", List.of());
            model.addAttribute("artistas", List.of());
            model.addAttribute("regiones", List.of());
            model.addAttribute("epocas", List.of());
        }

        return new ModelAndView("perfil", model);
    }

    @GetMapping("/editar-preferencias")
    public ModelAndView irAEditar(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();
        model.addAttribute("generos", servicioPerfil.obtenerGeneros());
        model.addAttribute("artistas", servicioPerfil.obtenerArtistas());
        model.addAttribute("regiones", servicioPerfil.obtenerRegiones());
        model.addAttribute("epocas", servicioPerfil.obtenerEpocas());

        PreferenciaUsuario preferenciasUsuario = servicioPerfil.obtenerPreferenciasPorUsuario(usuario);
        if (preferenciasUsuario != null) {
            model.addAttribute("generosSeleccionados", preferenciasUsuario.getGenerosSeleccionados());
            model.addAttribute("artistasSeleccionados", preferenciasUsuario.getArtistasSeleccionados());
            model.addAttribute("regionesSeleccionadas", preferenciasUsuario.getRegionesSeleccionadas());
            model.addAttribute("epocasSeleccionadas", preferenciasUsuario.getEpocasSeleccionadas());
        } else {

            model.addAttribute("generosSeleccionados", List.of());
            model.addAttribute("artistasSeleccionados", List.of());
            model.addAttribute("regionesSeleccionadas", List.of());
            model.addAttribute("epocasSeleccionadas", List.of());
        }

        return new ModelAndView("editar-preferencias", model);
    }

    @PostMapping("/guardar-preferencias")
    public ModelAndView guardarPreferencias(HttpSession session,
            @RequestParam(value = "generos", required = false) List<String> generosSeleccionados,
            @RequestParam(value = "artistas", required = false) List<String> artistasSeleccionados,
            @RequestParam(value = "regiones", required = false) List<String> regionesSeleccionadas,
            @RequestParam(value = "epocas", required = false) List<String> epocasSeleccionadas) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioPerfil.guardarPreferencias(
                    usuario.getId(),
                    generosSeleccionados != null ? generosSeleccionados : List.of(),
                    artistasSeleccionados != null ? artistasSeleccionados : List.of(),
                    regionesSeleccionadas != null ? regionesSeleccionadas : List.of(),
                    epocasSeleccionadas != null ? epocasSeleccionadas : List.of());
        }

        return new ModelAndView("redirect:/perfil");
    }

}
