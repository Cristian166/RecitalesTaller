package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Entrada;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorEntrada {

    private List<Entrada> entradas = new ArrayList<>();

    @GetMapping("/crear-entrada")
    public String mostrarFormularioParaAgregarEntrada( Model model) {
        model.addAttribute("entrada", new Entrada());
        return "crear-entrada";
    }

    @GetMapping("vista-entradas-recitales")
    public String mostrarMisEntradas( Model model) {
        model.addAttribute("entradas", entradas);
        return "vista-entradas-recitales";
    }

    @PostMapping("/agregar-entrada")
    public String agregarEntrada(@ModelAttribute Entrada entrada) {
        Long nuevoId = System.currentTimeMillis();
        System.out.println(nuevoId);
        entrada.setId(nuevoId);
        entradas.add(entrada);
        return "redirect:/vista-entradas-recitales";
    }


}
