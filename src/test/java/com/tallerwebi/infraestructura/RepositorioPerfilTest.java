package com.tallerwebi.infraestructura;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.Preferencia;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.PreferenciaUsuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioPerfilTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPerfil repositorio;

    @BeforeEach
    public void init() {
        this.repositorio = new RepositorioPerfilImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerTodasLasPreferenciasExistentes() {
        Preferencia p1 = new Preferencia();
        p1.setNombre("Rock");
        p1.setTipo("Genero");

        Preferencia p2 = new Preferencia();
        p2.setNombre("Pop");
        p2.setTipo("Genero");

        sessionFactory.getCurrentSession().save(p1);
        sessionFactory.getCurrentSession().save(p2);

        List<Preferencia> preferencias = repositorio.obtenerPreferenciaExistentes();

        assertThat(preferencias.size(), is(equalTo(2)));
        assertThat(preferencias, hasItems(p1, p2));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerNombresDePreferenciasPorTipo() {
        Preferencia p1 = new Preferencia();
        p1.setNombre("Rock");
        p1.setTipo("Genero");

        Preferencia p2 = new Preferencia();
        p2.setNombre("Pop");
        p2.setTipo("Genero");

        Preferencia p3 = new Preferencia();
        p3.setNombre("Argentina");
        p3.setTipo("Region");

        sessionFactory.getCurrentSession().save(p1);
        sessionFactory.getCurrentSession().save(p2);
        sessionFactory.getCurrentSession().save(p3);

        List<String> generos = repositorio.obtenerNombresDePreferenciasPorTipo("Genero");

        assertThat(generos.size(), is(equalTo(2)));
        assertThat(generos, containsInAnyOrder("Rock", "Pop"));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaGuardarPreferenciasParaUnUsuarioNuevo() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario);

        List<String> generos = Arrays.asList("Rock", "Pop");
        List<String> artistas = Arrays.asList("Soda Stereo");
        List<String> regiones = Arrays.asList("Argentina");
        List<String> epocas = Arrays.asList("80s");

        repositorio.guardarPreferenciasPorUsuario(usuario.getId(), generos, artistas, regiones, epocas);

        PreferenciaUsuario obtenida = repositorio.obtenerPreferenciasPorUsuario(usuario);

        assertThat(obtenida, is(notNullValue()));
        assertThat(obtenida.getUsuario().getId(), is(equalTo(usuario.getId())));
        assertThat(obtenida.getGenerosSeleccionados(), containsInAnyOrder("Rock", "Pop"));
        assertThat(obtenida.getArtistasSeleccionados(), contains("Soda Stereo"));
        assertThat(obtenida.getRegionesSeleccionadas(), contains("Argentina"));
        assertThat(obtenida.getEpocasSeleccionadas(), contains("80s"));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaActualizarPreferenciasDeUnUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario);

        // guardar inicial
        repositorio.guardarPreferenciasPorUsuario(
                usuario.getId(),
                Arrays.asList("Rock"),
                Arrays.asList("Soda Stereo"),
                Arrays.asList("Argentina"),
                Arrays.asList("80s")
        );

        // actualizar
        repositorio.guardarPreferenciasPorUsuario(
                usuario.getId(),
                Arrays.asList("Pop"),
                Arrays.asList("Charly García"),
                Arrays.asList("Uruguay"),
                Arrays.asList("90s")
        );

        PreferenciaUsuario actualizada = repositorio.obtenerPreferenciasPorUsuario(usuario);

        assertThat(actualizada.getGenerosSeleccionados(), contains("Pop"));
        assertThat(actualizada.getArtistasSeleccionados(), contains("Charly García"));
        assertThat(actualizada.getRegionesSeleccionadas(), contains("Uruguay"));
        assertThat(actualizada.getEpocasSeleccionadas(), contains("90s"));
    }

}