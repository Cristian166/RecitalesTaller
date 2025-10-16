package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.Banda;
import com.tallerwebi.dominio.BandaFavorita;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })

public class RepositorioBandaDisponibles {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioBandaslmple repositorioBandaslmple;
    private RepositorioUsuarioImpl repositorioUsuarioImple;
    private RepositorioBandasFavoritaImpl repositorioBandasFavorita;

    // Inyecto la secion en cada repositorio
    @BeforeEach
    void setupSession() throws Exception {
        this.repositorioBandaslmple = new RepositorioBandaslmple(sessionFactory);
        this.repositorioUsuarioImple = new RepositorioUsuarioImpl(sessionFactory);
        this.repositorioBandasFavorita = new RepositorioBandasFavoritaImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void deberiaGuardarUnaBandaDisponibleParaSerSeleccionadoPorElUsuario() throws Exception {
        // Dado un una Banda
        Banda banda1 = new Banda();
        banda1.setNombre("Banda1");
        Banda banda2 = new Banda();
        banda2.setNombre("Banda2");
        Banda banda3 = new Banda();
        banda3.setNombre("Banda3");

        // Guardo en mi repositorio de bandas esa banda
        repositorioBandaslmple.guardar(banda1);
        repositorioBandaslmple.guardar(banda2);
        repositorioBandaslmple.guardar(banda3);

        // entonces la tengo que recuperar
        List<Banda> bandasDisponibles = repositorioBandaslmple.obtenerTodasLasBandas();

        // entonces compardo
        assertThat(bandasDisponibles.size(), is(3));

    }

    @Test
    @Transactional
    public void deberiaGuardarUnaBandaDeLaListaDeBandaDisponiblesALalistaDeBandasFavoritasYasociadoAlUsuario() throws Exception {
        // Dado 3 Bandas disponibles
        Banda banda1 = new Banda();
        banda1.setNombre("Banda1");
        Banda banda2 = new Banda();
        banda2.setNombre("Banda2");
        Banda banda3 = new Banda();
        banda3.setNombre("Banda3");

        //Dado un usuario
        Usuario usuario1 = new Usuario();
        usuario1.setRol("ROLE_USER");
        usuario1.setEmail("a@.com");
        usuario1.setPassword("a123");


        // guardo bandas y usuario
        repositorioBandaslmple.guardar(banda1);
        repositorioBandaslmple.guardar(banda2);
        repositorioBandaslmple.guardar(banda3);
        repositorioUsuarioImple.guardar(usuario1);

        // entonces la tengo que recuperar
        List<Banda> bandasDisponibles = repositorioBandaslmple.obtenerTodasLasBandas();

        // cuando selecciona dos bandas
        Banda banda1favorita = bandasDisponibles.get(0);
        Banda banda2favorita = bandasDisponibles.get(1);

        // ahora agrego estasbandas  al listado de bandas favoritas
        BandaFavorita bandaFav1 = new BandaFavorita();
        bandaFav1.setUsuario(usuario1);
        bandaFav1.setBanda(banda1favorita);

        BandaFavorita bandaFav2 = new BandaFavorita();
        bandaFav2.setUsuario(usuario1);
        bandaFav2.setBanda(banda2favorita);
        // repositorio de banda favoritas
        repositorioBandasFavorita.guardar(bandaFav1);
        repositorioBandasFavorita.guardar(bandaFav2);
        // entonces el usuario posee 1 lista de bandas favoritas con dos bandas
        List<Banda> listadoDeBandasConUsuarioAsociado = repositorioBandasFavorita.obtenerSoloBandasFavoritas(usuario1.getId());
        assertThat(listadoDeBandasConUsuarioAsociado.size(), is(2));
    }

}