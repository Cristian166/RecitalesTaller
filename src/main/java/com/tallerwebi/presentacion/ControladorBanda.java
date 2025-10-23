package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.ServicioArtista;
import com.tallerwebi.dominio.ServicioArtistaFavorita;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorBanda {

    private ServicioArtista servicioBanda;
    private ServicioArtistaFavorita servicioBandaFavorita;

    public ControladorBanda(ServicioArtista servicioBanda, 
                           ServicioArtistaFavorita servicioBandaFavorita) {
        this.servicioBanda = servicioBanda;
        this.servicioBandaFavorita = servicioBandaFavorita;
    }

    @RequestMapping("/bandas")
    public ModelAndView mostrarBandas() {
        List<Artista> bandas = servicioBanda.obtenerTodasLasBandas();
        ModelAndView mav = new ModelAndView("bandas");
        mav.addObject("bandas", bandas);
        return mav;
    }

    @RequestMapping("/artistas-favoritos/agregar")
    public ModelAndView agregarArtistaAFavoritos(@RequestParam Long usuarioId, 
                                                 @RequestParam Long artistaId) {
        servicioBandaFavorita.agregarBandasFavoritos(usuarioId, artistaId);
        return new ModelAndView("redirect:/artistas");
    }

    @RequestMapping("/mis-artistas-favoritos")
    public ModelAndView obtenerArtistasFavoritos(@RequestParam Long usuarioId) {
        List<Artista> artistas = servicioBandaFavorita.obtenerArtistasFavoritosDeUsuario(usuarioId);
        
        ModelAndView mav = new ModelAndView("mis-artistas-favoritos");
        mav.addObject("artistas", artistas);
        
        return mav;
    }
}