package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.tallerwebi.infraestructura.DTOs.EntradaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEntrada {

    private final ServicioEntrada servicioEntrada;

    @Autowired
    public ControladorEntrada(ServicioEntrada servicioEntrada) {
        this.servicioEntrada = servicioEntrada;
    }

    @GetMapping("/mis-entradas")
    public String mostrarMisEntradas(@RequestParam(required = false) String error,
            Model model,
            HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuario);

        if (usuario == null) {
            return "redirect:/login";
        }
        List<EntradaDTO> entradasDTO = servicioEntrada.obtenerEntradasPorUsuario(usuario);
        model.addAttribute("entradas", entradasDTO);

        if (error != null) {
            model.addAttribute("mensajeError", "No se pudo eliminar la entrada (no existe).");
        }

        return "mis-entradas";
    }

    @GetMapping("/crear-entrada")
    public String mostrarFormularioParaAgregarEntrada(Model model, HttpSession session) {
        model.addAttribute("entrada", new Entrada());
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "crear-entrada";
    }

    @PostMapping("/agregar-entrada")
    public String agregarEntrada(@ModelAttribute("entrada") Entrada entrada,
            @RequestParam(value = "imagenArchivo", required = false) MultipartFile imagenArchivo,
            HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        if (usuario == null) {
            return "redirect:/login";
        }

        if (entrada.getFecha() == null) {
            entrada.setFecha(LocalDate.now());
        }

        if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
            try {
                Path carpetaUploads = Paths.get("uploads").toAbsolutePath();
                Files.createDirectories(carpetaUploads);

                String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                Path rutaArchivo = carpetaUploads.resolve(nombreArchivo);
                Files.write(rutaArchivo, imagenArchivo.getBytes());

                entrada.setImagen(nombreArchivo);
                System.out.println("Imagen guardada en: " + rutaArchivo);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        servicioEntrada.crearEntrada(entrada, usuario);
        return "redirect:/mis-entradas";
    }

    @PostMapping("/eliminar-entrada")
    public String eliminarEntrada(@RequestParam Long id) {
        try {
            servicioEntrada.eliminarEntrada(id);
            return "redirect:/mis-entradas";
        } catch (IllegalArgumentException e) {
            return "redirect:/mis-entradas?error";
        }
    }

    @GetMapping("/validar-entrada")
    public ModelAndView mostrarFormularioValidarEntrada(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("validar-entrada");
        modelAndView.addObject("entrada", servicioEntrada.buscarPorId(id));
        return modelAndView;
    }

    @PostMapping("/validar-entrada")
    public ModelAndView validarEntrada(@RequestParam("id") Long id, HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuario);

        ModelAndView modelAndView = new ModelAndView("validar-entrada");

        Entrada entrada = servicioEntrada.buscarPorId(id);

        if (entrada == null) {
            modelAndView.addObject("mensajeError", "No se encontró la entrada con ID: " + id);
            return modelAndView;
        }

        servicioEntrada.validarEntrada(id, usuario);
        entrada = servicioEntrada.buscarPorId(id);

        if (entrada.getValidada()) {
            modelAndView.addObject("mensajeExito", "La entrada fue validada correctamente.");
        } else {
            modelAndView.addObject("mensajeError", "No se pudo validar la entrada. Verifique los datos ingresados.");
        }

        modelAndView.addObject("entrada", entrada);
        return modelAndView;
    }

}
