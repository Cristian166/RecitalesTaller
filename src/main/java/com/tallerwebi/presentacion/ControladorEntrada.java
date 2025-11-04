package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEntrada {


    private final ServicioEntrada servicioEntrada;

    @Autowired
    public ControladorEntrada(ServicioEntrada servicioEntrada) {
        this.servicioEntrada = servicioEntrada;
    }

    @GetMapping("vista-entradas-recitales")
    public String mostrarMisEntradas( Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("entradas", servicioEntrada.obtenerEntradasPorUsuario(usuario));
        return "vista-entradas-recitales";
    }

    @GetMapping("/crear-entrada")
    public String mostrarFormularioParaAgregarEntrada( Model model) {
        model.addAttribute("entrada", new Entrada());
        return "crear-entrada";
    }


    @PostMapping("/agregar-entrada")
    public String agregarEntrada(@ModelAttribute Entrada entrada, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioEntrada.crearEntrada(entrada,usuario);
         return "redirect:/vista-entradas-recitales";
    }

    @PostMapping("/eliminar-entrada")
    public String eliminarEntrada(@RequestParam Long id) {
       servicioEntrada.eliminarEntrada(id);
        return "redirect:/vista-entradas-recitales";
    }

    @GetMapping("/validar-entrada")
    public ModelAndView mostrarFormularioValidarEntrada(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("validar-entrada");
        modelAndView.addObject("entrada", servicioEntrada.buscarPorId(id));
    return modelAndView;
    }

    @PostMapping("/validar-entrada")
    public ModelAndView validarEntrada(@RequestParam("id") Long id, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        ModelAndView modelAndView = new ModelAndView("validar-entrada");

        Entrada entrada = servicioEntrada.buscarPorId(id);

        if (entrada == null) {
            modelAndView.addObject("mensajeError", "No se encontró la entrada con ID: " + id);
            return modelAndView;
        }

        servicioEntrada.validarEntrada(id, usuario);
        entrada = servicioEntrada.buscarPorId(id);

        if (entrada.getValidada()) {
            modelAndView.addObject("mensajeExito", "La entrada fue validada correctamente.");
        } else {
            modelAndView.addObject("mensajeError", "No se pudo validar la entrada. Verifique los datos ingresados.");
        }

        modelAndView.addObject("entrada", entrada);
        return modelAndView;
}















 }


