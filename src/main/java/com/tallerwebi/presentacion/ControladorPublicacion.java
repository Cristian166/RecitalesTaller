package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
public class ControladorPublicacion {
    /*
    * Maneja las publicaciones dentro de una comunidad (crear,listar...)
    * */

    @Autowired
    private ServicioPublicacion servicioPublicacion;

    //Crear una publicacion en una comunidad
    @PostMapping("/comunidad/{id}/publicar")
    public String publicarEnLaComunidad( @PathVariable Long id,
                                         @ModelAttribute("nuevaPublicacion") Publicacion publicacion){
        System.out.println("Contenido de publicacion: " + publicacion.getContenido());
        publicacion.setId(System.currentTimeMillis());
        publicacion.setFechaCreacion(LocalDateTime.now());

        servicioPublicacion.crearPublicacion(publicacion, id);
        return "redirect:/comunidad/"+id;
    }
}
