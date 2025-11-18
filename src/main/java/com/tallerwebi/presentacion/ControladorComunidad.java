package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioEncuesta;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.PublicacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ControladorComunidad {
    private final ServicioComunidad servicioComunidad;
    private final ServicioPublicacion servicioPublicacion;

    @Autowired
    private HttpSession session;
    @Autowired
    private ServicioEncuesta servicioEncuesta;

    @Autowired
    public ControladorComunidad(ServicioComunidad servicioComunidad, ServicioPublicacion servicioPublicacion) {
        this.servicioComunidad = servicioComunidad;
        this.servicioPublicacion = servicioPublicacion;
    }

    // mostrar comunidades
    @GetMapping("/comunidades")
    public String mostrarComunidades(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuario);

        if (usuario == null) {
            return "redirect:/login";
        }

        Set<Comunidad> comunidadesUnidas = servicioComunidad.listarComunidadesUnidas(usuario.getId());
        Set<Comunidad> comunidadesSugeridas = servicioComunidad.listarComunidadesSugeridas(usuario.getId());

        comunidadesUnidas.forEach(comunidad -> comunidad.setCantidadMiembros(comunidad.getUsuarios().size()));
        comunidadesSugeridas.forEach(comunidad -> comunidad.setCantidadMiembros(comunidad.getUsuarios().size()));

        comunidadesUnidas.forEach(comunidad -> {
            // Establecer la cantidad de miembros directamente
            comunidad.setCantidadMiembros(comunidad.getUsuarios().size());
        });

        model.addAttribute("comunidadesUnidas", comunidadesUnidas);
        model.addAttribute("comunidadesSugeridas", comunidadesSugeridas);

        return "comunidades";
    }

    @GetMapping("/comunidad/{id}")
    public String verComunidad(@PathVariable Long id, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Comunidad comunidad = servicioComunidad.obtenerComunidad(id);

        if (comunidad == null) {
            return "redirect:/comunidades";
        }

        List<PublicacionDTO> publicacionesDTO = servicioPublicacion.listarPublicacionesDTOPorComunidad(id);

        // Obtener encuesta activa
        Encuesta encuestaActiva = servicioEncuesta.obtenerEncuestaActiva(id);

        int totalVotos = 0;

        if (encuestaActiva != null && encuestaActiva.getOpciones() != null) {
            totalVotos = encuestaActiva.getOpciones()
                    .stream()
                    .mapToInt(o -> o.getVotos())
                    .sum();
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("comunidad", comunidad);
        model.addAttribute("publicaciones", publicacionesDTO);
        model.addAttribute("nuevaPublicacion", new Publicacion());
        model.addAttribute("encuesta", encuestaActiva);
        model.addAttribute("totalVotos", totalVotos);

        return "comunidad";
    }

    @GetMapping("/comunidad/{id}/unirse")
    public String ingresarAUnaComunidad(@PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioComunidad.unirseAComunidad(usuario, id);
        }
        return "redirect:/comunidad/" + id;
    }

    @PostMapping("/comunidad/{id}/abandonar")
    public String abandonarUnaComunidad(@PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioComunidad.abandonarComunidad(usuario, id);
        }
        return "redirect:/comunidades";
    }

    @PostMapping("/comunidad/{id}/eliminar")
    public String eliminarUnaComunidad(@PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioComunidad.eliminarComunidad(id);
        }
        return "redirect:/comunidades";
    }

    @GetMapping("/crear-comunidad")
    public String mostrarFormularioCrearComunidad(Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && usuario.getEsPremium()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("nuevaComunidad", new Comunidad());
            return "crear-comunidad";
        } else {
            return "redirect:/comunidades";
        }
    }

    @PostMapping("/comunidades/crear")
    public String crearUnaComunidad(@ModelAttribute Comunidad comunidad, @RequestParam("imagenArchivo") MultipartFile imagenArchivo, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && usuario.getEsPremium()) {

            if (servicioComunidad.existeComunidadPorNombre(comunidad.getNombre())) {
                model.addAttribute("error", "Ya existe una comunidad con este nombre.");
                model.addAttribute("nuevaComunidad",comunidad);
                return "crear-comunidad";  // Devolver la misma vista con el mensaje de error.
            }

            if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
                try {

                    String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                    Path ruta = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
                    Files.createDirectories(ruta.getParent());
                    Files.write(ruta, imagenArchivo.getBytes());

                    comunidad.setImagen(nombreArchivo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            comunidad.setUsuarioCreador(usuario);
            comunidad.getUsuarios().add(usuario);

            servicioComunidad.crearComunidad(comunidad, usuario);
            return "redirect:/comunidades";
        }
        return "redirect:/comunidades";
    }

    @GetMapping("/buscar-comunidad")
    @ResponseBody
    public List<Map<String, Object>> buscarComunidad(@RequestParam String query) {
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ArrayList<>();  // Si no hay usuario, retornar una lista vacía
        }

        // Buscar las comunidades que no tienen al usuario
        Set<Map<String, Object>> comunidades = servicioComunidad.buscarComunidadesPorNombre(query, usuario);

        return new ArrayList<>(comunidades);  // Convertimos el Set a una lista para devolverla como JSON
    }



}
