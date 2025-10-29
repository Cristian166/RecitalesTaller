package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String publicarEnLaComunidad(@PathVariable Long id,
                                        @ModelAttribute("nuevaPublicacion") Publicacion publicacion,
                                        @RequestParam( value = "imagenArchivo") MultipartFile imagenArchivo){

        System.out.println("Contenido de publicacion: " + publicacion.getContenido());
        publicacion.setFechaCreacion(LocalDateTime.now());

        if(!imagenArchivo.isEmpty()){
            try{
                String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                Path ruta = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagenArchivo.getBytes());

                publicacion.setImagen(nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        servicioPublicacion.crearPublicacion(publicacion, id);
        return "redirect:/comunidad/"+id;
    }
}
