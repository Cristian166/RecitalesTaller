package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ControladorBanda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import com.tallerwebi.dominio.entidades.Banda;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorNotificacionRecibidaTest {


    private ServicioBanda servicioBandaMkt;
    private ControladorBanda controladorBanda;

    @BeforeEach
    public void setUp() {
        servicioBandaMkt = mock(ServicioBanda.class);
        controladorBanda = new ControladorBanda(servicioBandaMkt);
    }

    @Test
    public void debeRetornarUnListadoDeBandas() {
        // Dado que tengo dos Bandas, con refactory
        List<Banda> listDeBanda= getBandas();

        // El servicio me trae el listado de Bandas
        when(servicioBandaMkt.obtenerTodasLasBandas()).thenReturn(listDeBanda);
        // llamo al controlador
        ModelAndView mav = controladorBanda.mostrarBandas();
        // Cuando
        int cantidadDeBandas = controladorBanda.cantidadDeBandas(listDeBanda);
        // Entonces
        assertThat(cantidadDeBandas, equalTo(2));
        // entonces debo mostrar la vista
        assertThat(mav.getViewName(), is("bandas"));

    }

    @Test
    public void deboAgregarUnaBandaABandasFavoritasDeLasBandasDisponibles() {

    }

    private static List<Banda> getBandas() {
        List<Banda> listDeBanda= new ArrayList<>();

        Banda banda1 = new Banda();
        banda1.setNombre("Fito Paez");
        Banda banda2 = new Banda();
        banda2.setNombre("Soda Estereo");

        listDeBanda.add(banda1);
        listDeBanda.add(banda2);
        return listDeBanda;
    }


}
