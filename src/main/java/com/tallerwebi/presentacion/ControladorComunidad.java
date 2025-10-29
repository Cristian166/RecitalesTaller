package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ControladorComunidad {

    /*
    * Maneja todo lo relacionado a comunidades (listar,ver,detalles,unirse o abandonar)
    * */

    @Autowired
    private ServicioComunidad servicioComunidad;
    @Autowired
    private ServicioPublicacion servicioPublicacion;

    public ControladorComunidad(ServicioComunidad servicioComunidad) {
        this.servicioComunidad = servicioComunidad;
    }

    //mostrar comunidades
    @GetMapping("/comunidades")
    public String mostrarComunidades(Model model){

        model.addAttribute("comunidades", servicioComunidad.listarTodasLasComunidades());
        return "comunidades";
    }

    @GetMapping("/comunidad/{id}")
    public String verComunidad(@PathVariable Long id, Model model){
        Comunidad comunidad = servicioComunidad.obtenerComunidad(id);
        if (comunidad == null) {
            // Redirige a la lista de comunidades si no existe
            return "redirect:/comunidades";
        }
        List<Publicacion> publicaciones = servicioPublicacion.listarPublicacionesPorComunidad(id);

        DateTimeFormatter formatear = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        publicaciones.forEach(pub ->{
            if(pub.getFechaCreacion() != null){
                pub.setFechaCreacionString(pub.getFechaCreacion().format(formatear));
            }else {
                pub.setFechaCreacionString("hace un momento");
            }
        });

        // Pasamos al modelo la comunidad y sus publicaciones
        model.addAttribute("comunidad", comunidad);
        model.addAttribute("publicaciones", publicaciones);
        model.addAttribute("nuevaPublicacion", new Publicacion());

        return "comunidad";
    }


    @PostMapping("/{id}/unirse")
    public String ingresarAUnaComunidad(@PathVariable Long id){

        return "redirect:/comunidades/" + id;
    }
    @PostMapping("{id}/abandonar")
    public String abandonarUnaComunidad(@PathVariable Long id){

        return "redirect:/comunidades";
    }

}
