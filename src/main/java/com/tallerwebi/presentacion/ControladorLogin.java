package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            request.getSession().setAttribute("usuario", usuarioBuscado);
            servicioLogin.asignarInsigniaLogin(usuarioBuscado);
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("imagenArchivo") MultipartFile imagenArchivo, //para cargar imagen
            Model model) {
        try {
            if (!usuario.getPassword().equals(confirmPassword)) {
                model.addAttribute("error", "Las contraseñas no coinciden.");
                return "nuevo-usuario";
            }
            
            if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
                try {
                    String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                    Path ruta = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
                    Files.createDirectories(ruta.getParent()); // Crea los directorios si no existen
                    Files.write(ruta, imagenArchivo.getBytes()); // Guarda la imagen en el servidor

                    // Guarda solo el nombre del archivo en la base de datos
                    usuario.setImagen(nombreArchivo);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("error", "Error al guardar la imagen.");
                    return "nuevo-usuario";
                }
            }


            servicioLogin.registrar(usuario);

            model.addAttribute("mensaje", "Usuario registrado con éxito.");
            return "redirect:/login";

        } catch (UsuarioExistente existente) {
            model.addAttribute("error", existente.getMessage());
            return "nuevo-usuario";

        } catch (IllegalArgumentException excepcion) {
            model.addAttribute("error", excepcion.getMessage());
            return "nuevo-usuario";
        }
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }
}
