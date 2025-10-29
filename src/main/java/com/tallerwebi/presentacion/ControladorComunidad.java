package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ControladorComunidad {

    /*
    * Maneja todo lo relacionado a comunidades (listar,ver,detalles,unirse o abandonar)
    * */

    private final ServicioComunidad servicioComunidad;
    private final ServicioPublicacion servicioPublicacion;

    @Autowired
    private HttpSession session;

    public ControladorComunidad(ServicioComunidad servicioComunidad, ServicioPublicacion servicioPublicacion) {
        this.servicioComunidad = servicioComunidad;
        this.servicioPublicacion = servicioPublicacion;
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
                String fechaFormateada = pub.getFechaCreacion().format(formatear);
                model.addAttribute("fechaCreacion", fechaFormateada);
            }else {
                model.addAttribute("fechaCreacion" + pub.getId(),"hace unos minutos");
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

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/login";
        }

        servicioComunidad.unirseAComunidad(usuario, id);
        return "redirect:/comunidades/" + id;
    }

    @PostMapping("{id}/abandonar")
    public String abandonarUnaComunidad(@PathVariable Long id){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/login";
        }
        servicioComunidad.abandonarComunidad( usuario, id);

        return "redirect:/comunidades";
    }

}
