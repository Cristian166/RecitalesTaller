package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.entidades.Banda;
import com.tallerwebi.dominio.ServicioBanda;
import com.tallerwebi.dominio.ServicioBandaFavorita;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorBanda {

    private int cantidadDeBandas;
    private ServicioBanda servicioBanda;
    private ServicioBandaFavorita servicioBandaFavorita;

    public ControladorBanda(ServicioBanda servicioBanda) {
        this.servicioBanda = servicioBanda;
    }


    public int cantidadDeBandas(List<Banda> ListadoRecital) {
        this.cantidadDeBandas = ListadoRecital.size();
        return cantidadDeBandas;

    }

    @RequestMapping("/bandas")
    public ModelAndView mostrarBandas() {
        List<Banda> bandas = servicioBanda.obtenerTodasLasBandas();
        ModelAndView mav = new ModelAndView("bandas");
        mav.addObject("bandas", bandas);
        return mav;
    }

    @RequestMapping("bandas-favoritas/agregar")
    public ModelAndView agregarAFavorito(Long usuarioId, Long bandaId) {
        servicioBandaFavorita.agregarBandasFavoritos(usuarioId, bandaId);
        return new ModelAndView("redirect:/bandas");
    }
}
