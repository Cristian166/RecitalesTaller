package com.tallerwebi.presentacion;

import com.tallerwebi.presentacion.ControladorBanda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import com.tallerwebi.dominio.ServicioArtista;
import com.tallerwebi.dominio.ServicioArtistaFavorita;
import com.tallerwebi.dominio.entidades.Artista;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorNotificacionRecibidaTest {

    private ServicioArtista servicioBandaMock;
    private ServicioArtistaFavorita servicioBandaFavoritaMock;
    private ControladorBanda controladorBanda;

    @BeforeEach
    public void setUp() {
        servicioBandaMock = mock(ServicioArtista.class);
        servicioBandaFavoritaMock = mock(ServicioArtistaFavorita.class);
        // Pass BOTH mocks to controller
        controladorBanda = new ControladorBanda(servicioBandaMock, servicioBandaFavoritaMock);
    }

    @Test
    public void deberiaGuardarUnArtistaComoFavoritoYRelacionarloConUnUsuario()  {
        // Dado: un usuario y un artista
        Long usuarioId = 1L;
        Long artistaId = 5L;

        // Cuando: llamo al CONTROLLER (not the mock!)
        ModelAndView mav = controladorBanda.agregarArtistaAFavoritos(usuarioId, artistaId);

        // Entonces: el servicio debe ser llamado
        verify(servicioBandaFavoritaMock, times(1)).agregarBandasFavoritos(usuarioId, artistaId);
        
        // Entonces: debe redirigir a la página de artistas
        assertThat(mav.getViewName(), equalTo("redirect:/artistas"));
    }

    @Test
public void deberiaEntregarmeTodosLosArtistasFavoritosRelacionadosConUnUsuarioDado() {
    // Dado: un usuario con artistas favoritos
    Long usuarioId = 1L;
    
    List<Artista> artistasFavoritos = new ArrayList<>();
    
    Artista artista1 = new Artista();
    artista1.setId(1L);
    artista1.setNombre("Gustavo Cerati");
    artista1.setGenero("Rock");
    
    Artista artista2 = new Artista();
    artista2.setId(2L);
    artista2.setNombre("Fito Paez");
    artista2.setGenero("Rock");
    
    artistasFavoritos.add(artista1);
    artistasFavoritos.add(artista2);
    
    when(servicioBandaFavoritaMock.obtenerArtistasFavoritosDeUsuario(usuarioId))
            .thenReturn(artistasFavoritos);

    // Cuando: obtengo los artistas favoritos
    ModelAndView mav = controladorBanda.obtenerArtistasFavoritos(usuarioId);

    // Entonces: el servicio debe ser llamado
    verify(servicioBandaFavoritaMock, times(1)).obtenerArtistasFavoritosDeUsuario(usuarioId);
    
    // Entonces: debe mostrar la vista correcta
    assertThat(mav.getViewName(), equalTo("mis-artistas-favoritos"));
    
    // Entonces: debe contener la lista de artistas
    List<Artista> artistas = (List<Artista>) mav.getModel().get("artistas");
    assertThat(artistas, is(notNullValue()));
    assertThat(artistas.size(), equalTo(2));
    assertThat(artistas.get(0).getNombre(), equalTo("Gustavo Cerati"));
    assertThat(artistas.get(1).getNombre(), equalTo("Fito Paez"));
}

    private static List<Artista> getBandas() {
        List<Artista> listDeBanda= new ArrayList<>();

        Artista banda1 = new Artista();
        banda1.setNombre("Fito Paez");
        Artista banda2 = new Artista();
        banda2.setNombre("Soda Estereo");

        listDeBanda.add(banda1);
        listDeBanda.add(banda2);
        return listDeBanda;
    }

}
