package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Entrada;
import com.tallerwebi.dominio.ServicioEntrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControladorEntrada {


    private final ServicioEntrada servicioEntrada;

    @Autowired
    public ControladorEntrada(ServicioEntrada servicioEntrada) {
        this.servicioEntrada = servicioEntrada;
    }

    @GetMapping("vista-entradas-recitales")
    public String mostrarMisEntradas( Model model) {
        model.addAttribute("entradas", servicioEntrada.obtenerTodasMisEntradas());
        return "vista-entradas-recitales";
    }

    @GetMapping("/crear-entrada")
    public String mostrarFormularioParaAgregarEntrada( Model model) {
        model.addAttribute("entrada", new Entrada());
        return "crear-entrada";
    }



    @PostMapping("/agregar-entrada")
    public String agregarEntrada(@ModelAttribute Entrada entrada) {
        servicioEntrada.crearEntrada(entrada);
        System.out.println("Id de entrada creada: "+entrada.getId());
         return "redirect:/vista-entradas-recitales";
    }

    @PostMapping("/eliminar-entrada")
    public String eliminarEntrada(@RequestParam Long id) {
       servicioEntrada.eliminarEntrada(id);
        return "redirect:/vista-entradas-recitales";
    }

}
