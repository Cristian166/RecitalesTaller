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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin){
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
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

   @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
        try {
            if (!usuario.getPassword().equals(confirmPassword)) {
                model.addAttribute("error", "Las contraseñas no coinciden.");
                return "nuevo-usuario";
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

    @RequestMapping(path = "/resitalesAsistidos", method = RequestMethod.GET)
    public ModelAndView irARecitalesProyecto() { return new ModelAndView( "resitalesAsistidos"); }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() { return new ModelAndView("redirect:/login");
    }
}

