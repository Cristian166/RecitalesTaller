package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRecital;
import com.tallerwebi.dominio.entidades.Recital;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.RecitalesDTO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorRecitalAsistido {
    ServicioRecital servicioRecital;

    public ControladorRecitalAsistido(ServicioRecital resitalServicioMkt) {
        this.servicioRecital = resitalServicioMkt;

    }

    public boolean usuariosNoPoseeRecitalAsistido(long usuarioId) {
        return true;
    }

    public boolean usuariosSiPoseeRecitalAsistido(long usuarioId) {
        return true;
    }

    @RequestMapping("/recitales-asistidos")
    public ModelAndView mostrarRecitalesAsistidos(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Recital> recitales = servicioRecital.obtenerRecitalesAsistidosPorUsuario(usuario.getId());

        ModelAndView mav = new ModelAndView("recitales-asistidos");

        mav.addObject("usuario", usuario);

        if (recitales == null) {
            mav.addObject("recitales", new ArrayList<>());
        } else {
            List<RecitalesDTO> dto = recitales.stream()
                    .map(r -> new RecitalesDTO(
                            r.getRecitalId(),
                            r.getNombreRecital(),
                            r.getLocalidad()))
                    .collect(Collectors.toList());

            mav.addObject("recitales", dto);
        }

        return mav;
    }

}
