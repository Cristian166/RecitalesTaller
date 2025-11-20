package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Controller
public class ControladorPublicacion {

    private final ServicioPublicacion servicioPublicacion;
    private final ServicioComunidad servicioComunidad;
    private final HttpSession session;

    @Autowired
    public ControladorPublicacion(ServicioPublicacion servicioPublicacion,
            ServicioComunidad servicioComunidad,
            HttpSession session) {
        this.servicioPublicacion = servicioPublicacion;
        this.servicioComunidad = servicioComunidad;
        this.session = session;
    }

    // Crear una publicacion en una comunidad
    @PostMapping("/comunidad/{id}/publicar")
    public String publicarEnLaComunidad(@PathVariable Long id,
                                        @ModelAttribute("nuevaPublicacion") Publicacion publicacion,
                                        @RequestParam(value = "imagenArchivo", required = false) MultipartFile imagenArchivo) {

        Comunidad comunidad = servicioComunidad.obtenerComunidad(id);
        if (comunidad == null) {
            System.out.println("No existe comunidad con id: " + id);
            return "redirect:/comunidades";
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            System.out.println("No hay usuario logueado en sesión");
            return "redirect:/login";
        }

        if (publicacion.getContenido() == null || publicacion.getContenido().trim().isEmpty()) {
            System.out.println("Contenido de publicación vacío");
            return "redirect:/comunidad/" + id;
        }

        if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
            try {
                String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                Path ruta = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagenArchivo.getBytes());

                publicacion.setImagen(nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        publicacion.setAutorPublicacion(usuario);
        publicacion.setComunidad(comunidad);
        publicacion.setFechaCreacion(LocalDateTime.now());

        servicioPublicacion.crearPublicacion(publicacion, id, usuario);
        return "redirect:/comunidad/" + id;
    }

}
