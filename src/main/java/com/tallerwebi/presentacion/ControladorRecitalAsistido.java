package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Recital;
import com.tallerwebi.dominio.ServicioRecital;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/recitales-asistidos/mapa") // este es el nombre de la url de my sitio
    public ModelAndView obtenerRecitalesAsistidosParaPresentarEnLaVista(long usuarioId){
        List<Recital> recitales = servicioRecital.obtenerRecitalesAsistidosPorUsuario(usuarioId);

        // creo el array vacio para colocar el recital con los atributos deseados
        List<RecitalesDTO> dto = new ArrayList<>();

        // agrego Recital al array
        for (Recital recital : recitales) {
            RecitalesDTO recitalesDto = new RecitalesDTO(
                    recital.getRecitalId(),
                    recital.getNombreRecital(),
                    recital.getLocalidad()
            );
            dto.add(recitalesDto);}

        // esto indicara que mostral al html
        ModelAndView  mav = new ModelAndView("recitales-asistidos");
        mav.addObject("recitales", dto);

        return mav;
    }

}
