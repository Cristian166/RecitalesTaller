package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.ServicioInsigniaImpl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ControladorInsignia {

    private ServicioInsigniaImpl servicio;

    public ControladorInsignia() {
        this.servicio = new ServicioInsigniaImpl();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cris@test.com");

        Insignia i1 = new Insignia();
        i1.setId(1L);
        i1.setNombre("Participante");
        i1.setDescripcion("Participaste en tu primer recital");
        i1.setImagen("/");

        Insignia i2 = new Insignia();
        i2.setId(2L);
        i2.setNombre("Activo");
        i2.setDescripcion("Publicaste 10 reseñas");
        i2.setImagen("/");

        servicio.asignarInsignia(usuario, i1);
        servicio.asignarInsignia(usuario, i2);
    }

    @GetMapping("/insignias")
    public String mostrarInsignias(Model model) {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        List<Insignia> insignias = servicio.obtenerInsigniasDeUsuario(usuario);

        model.addAttribute("insignias", insignias);
        return "insignias";
    }
}
