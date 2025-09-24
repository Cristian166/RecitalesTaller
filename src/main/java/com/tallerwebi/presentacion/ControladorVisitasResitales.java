package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Recital;
import com.tallerwebi.dominio.ServicioVisitasImp;
import com.tallerwebi.dominio.ServicioVisitasInterfaz;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recitales")

public class ControladorVisitasResitales {

    private final ServicioVisitasImp recitalService;

    public ControladorVisitasResitales(ServicioVisitasImp recitalService) {
        this.recitalService =  recitalService;
    }

    @GetMapping("/mapa")
    public String verRecitalesEnMapa(Model model) {
        List<Recital> recitales = recitalService.obtenerRecitales();
        model.addAttribute("recitales", recitales);
        return "mapaRecitales";
    }

}
