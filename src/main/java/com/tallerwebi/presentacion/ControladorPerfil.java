package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.serviciosimpl.ServicioPerfilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ControladorPerfil {

    private final ServicioPerfil servicioPerfil;
    private ServicioInsignia servicioInsignia;

    @Autowired
    public ControladorPerfil(ServicioPerfil servicioPerfil, ServicioInsignia servicioInsignia) {
        this.servicioPerfil = servicioPerfil;
        this.servicioInsignia = servicioInsignia;
    }

    @GetMapping("/perfil")
    public ModelAndView irAPerfil(@RequestParam(value = "edit", required = false) String editar, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        servicioInsignia.asignarInsigniaFinalSiCorresponde(usuario);

        PreferenciaUsuario preferencias = servicioPerfil.obtenerPreferenciasPorUsuario(usuario);
        List<Insignia> insignias = servicioInsignia.obtenerInsigniasDeUsuario(usuario);

        boolean modoEditar = editar != null && editar.equals("true");

        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        model.addAttribute("modoEditar", modoEditar);

        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("apellido", usuario.getApellido());
        model.addAttribute("email", usuario.getEmail());
        model.addAttribute("pais", usuario.getPais());
        model.addAttribute("provincia", usuario.getProvincia());
        model.addAttribute("direccion", usuario.getDireccion());
        model.addAttribute("telefono", usuario.getTelefono());
        model.addAttribute("insignias", insignias);

        if (preferencias != null) {
            model.addAttribute("generos", preferencias.getGenerosSeleccionados());
            model.addAttribute("artistas", preferencias.getArtistasSeleccionados());
            model.addAttribute("regiones", preferencias.getRegionesSeleccionadas());
            model.addAttribute("epocas", preferencias.getEpocasSeleccionadas());
        } else {
            model.addAttribute("generos", List.of());
            model.addAttribute("artistas", List.of());
            model.addAttribute("regiones", List.of());
            model.addAttribute("epocas", List.of());
        }

        return new ModelAndView("perfil", model);
    }

    @GetMapping("/editar-preferencias")
    public ModelAndView irAEditar(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        model.addAttribute("usuario", usuario);
        model.addAttribute("generos", servicioPerfil.obtenerGeneros());
        model.addAttribute("artistas", servicioPerfil.obtenerArtistas());
        model.addAttribute("regiones", servicioPerfil.obtenerRegiones());
        model.addAttribute("epocas", servicioPerfil.obtenerEpocas());

        PreferenciaUsuario preferenciasUsuario = servicioPerfil.obtenerPreferenciasPorUsuario(usuario);
        if (preferenciasUsuario != null) {
            model.addAttribute("generosSeleccionados", preferenciasUsuario.getGenerosSeleccionados());
            model.addAttribute("artistasSeleccionados", preferenciasUsuario.getArtistasSeleccionados());
            model.addAttribute("regionesSeleccionadas", preferenciasUsuario.getRegionesSeleccionadas());
            model.addAttribute("epocasSeleccionadas", preferenciasUsuario.getEpocasSeleccionadas());
        } else {

            model.addAttribute("generosSeleccionados", List.of());
            model.addAttribute("artistasSeleccionados", List.of());
            model.addAttribute("regionesSeleccionadas", List.of());
            model.addAttribute("epocasSeleccionadas", List.of());
        }

        return new ModelAndView("editar-preferencias", model);
    }

    @PostMapping("/guardar-preferencias")
    public ModelAndView guardarPreferencias(HttpSession session,
            @RequestParam(value = "generos", required = false) List<String> generosSeleccionados,
            @RequestParam(value = "artistas", required = false) List<String> artistasSeleccionados,
            @RequestParam(value = "regiones", required = false) List<String> regionesSeleccionadas,
            @RequestParam(value = "epocas", required = false) List<String> epocasSeleccionadas) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioPerfil.guardarPreferencias(
                    usuario.getId(),
                    generosSeleccionados != null ? generosSeleccionados : List.of(),
                    artistasSeleccionados != null ? artistasSeleccionados : List.of(),
                    regionesSeleccionadas != null ? regionesSeleccionadas : List.of(),
                    epocasSeleccionadas != null ? epocasSeleccionadas : List.of());
        }

        return new ModelAndView("redirect:/perfil");
    }

    @GetMapping("/perfil-visitado/{id}")
    public String verPerfilVisitado(@PathVariable Long id, HttpSession session, Model model) {

        Usuario usuarioVisitado = servicioPerfil.obtenerUsuarioPorId(id);
        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");

        if (usuarioVisitado == null) {
            return "redirect:/perfil";
        }

        servicioPerfil.asignarInsigniaPorVisitarPerfil(usuarioActual, usuarioVisitado);

        PreferenciaUsuario preferenciasVisitado = servicioPerfil.obtenerPreferenciasPorUsuario(usuarioVisitado);
        List<Insignia> insignias = servicioInsignia.obtenerInsigniasDeUsuario(usuarioVisitado);

        model.addAttribute("usuarioVisitado", usuarioVisitado);
        model.addAttribute("usuarioActual", usuarioActual);
        model.addAttribute("usuario", usuarioActual);

        model.addAttribute("nombre", usuarioVisitado.getNombre());
        model.addAttribute("apellido", usuarioVisitado.getApellido());
        model.addAttribute("email", usuarioVisitado.getEmail());
        model.addAttribute("provincia", usuarioVisitado.getProvincia());
        model.addAttribute("direccion", usuarioVisitado.getDireccion());
        model.addAttribute("telefono", usuarioVisitado.getTelefono());

        model.addAttribute("insignias", insignias);

        if (preferenciasVisitado != null) {
            model.addAttribute("generos", preferenciasVisitado.getGenerosSeleccionados());
            model.addAttribute("artistas", preferenciasVisitado.getArtistasSeleccionados());
            model.addAttribute("regiones", preferenciasVisitado.getRegionesSeleccionadas());
            model.addAttribute("epocas", preferenciasVisitado.getEpocasSeleccionadas());
        } else {
            model.addAttribute("generos", List.of());
            model.addAttribute("artistas", List.of());
            model.addAttribute("regiones", List.of());
            model.addAttribute("epocas", List.of());
        }
        return "perfil-visitado";
    }

    @PostMapping("/actualizar-perfil")
    public String actualizarPerfil(@ModelAttribute("usuario") Usuario usuario,
                                   @RequestParam("imagenArchivo") MultipartFile imagenArchivo,
                                   HttpSession session) {
        // Obtener el usuario de la sesión
        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

        if (sessionUsuario != null) {
            // Verificar que el ID del usuario no sea nulo
            if (sessionUsuario.getId() == null) {
                throw new IllegalArgumentException("ID del usuario es requerido para la actualización.");
            }

            // Actualiza los datos del usuario (sin la imagen)
            servicioPerfil.actualizarPerfil(sessionUsuario.getId(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getTelefono(),
                    usuario.getEmail(),
                    usuario.getDireccion(),
                    usuario.getPais(),
                    usuario.getProvincia(),
                    usuario.getImagen());  // Llama al servicio de actualización

            // Si se ha enviado una nueva imagen, actualízala
            if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
                try {
                    // Generar un nombre único para el archivo
                    String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                    Path ruta = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

                    // Crea los directorios si no existen
                    Files.createDirectories(ruta.getParent());

                    // Guarda el archivo en el servidor
                    Files.write(ruta, imagenArchivo.getBytes());

                    // Actualiza el nombre del archivo en el objeto usuario
                    usuario.setImagen(nombreArchivo);  // Asegúrate de actualizar el campo imagen en el objeto usuario
                    servicioPerfil.actualizarImagenPerfil(usuario.getId(), nombreArchivo);  // Actualiza la imagen en la base de datos
                } catch (IOException e) {
                    e.printStackTrace();
                    // Maneja el error de carga de imagen
                    session.setAttribute("error", "Error al guardar la imagen.");
                    return "redirect:/perfil";  // Redirige de nuevo al perfil en caso de error
                }
            }

            // Actualiza la sesión con los nuevos datos del usuario (incluida la imagen)
            session.setAttribute("usuario", usuario);

            // Redirige al perfil con los datos actualizados
            return "redirect:/perfil";
        }

        return "redirect:/login";  // Si el usuario no está logueado, redirige al login
    }
}
