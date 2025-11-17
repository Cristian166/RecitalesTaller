package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tallerwebi.dominio.ServicioCalendario;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;



@Controller
public class ControladorCalendario {

    private final ServicioCalendario servicioCalendario;

    @Autowired
    public ControladorCalendario(ServicioCalendario servicioCalendario) {
        this.servicioCalendario= servicioCalendario;
    }
    


    @GetMapping("/calendario")
    public String mostrarCalendario(Model model,HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Entrada> entradas = servicioCalendario.obtenerEventos(usuario);
        LocalDate hoy = LocalDate.now();

        List<Entrada> entradasFuturas = entradas.stream()
                .filter(entrada -> entrada.getFecha() != null &&
                            (entrada.getFecha().isAfter(hoy) || entrada.getFecha().isEqual(hoy)))
                .sorted(Comparator.comparing(Entrada::getFecha))
                .collect(Collectors.toList());

        List<Entrada> entradasPasadas = entradas.stream()
                .filter(e -> e.getFecha() != null && e.getFecha().isBefore(hoy))
                .sorted(Comparator.comparing(Entrada::getFecha).reversed())
                .collect(Collectors.toList());

        model.addAttribute("entradasFuturas", entradasFuturas);
        model.addAttribute("entradasPasadas", entradasPasadas);
        model.addAttribute("usuario", usuario);

        return "calendario";
    }


}