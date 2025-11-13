package com.tallerwebi.presentacion;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.EntradaDTO;
import com.tallerwebi.dominio.entidades.Comunidad;

@Controller
public class ControladorHome {

    private final ServicioEntrada servicioEntrada;
    private final ServicioComunidad servicioComunidad;

    @Autowired
    public ControladorHome(ServicioEntrada servicioEntrada,
            ServicioComunidad servicioComunidad) {
        this.servicioEntrada = servicioEntrada;
        this.servicioComunidad = servicioComunidad;
    }

    @GetMapping("/home")
    public String mostrarHome(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        List<EntradaDTO> entradas = servicioEntrada.obtenerEntradasPorUsuario(usuario);
        
        model.addAttribute("entradas", entradas);
        model.addAttribute("comunidadesUnidas", servicioComunidad.listarComunidadesUnidas(usuario.getId()));

        return "home";
    }
}