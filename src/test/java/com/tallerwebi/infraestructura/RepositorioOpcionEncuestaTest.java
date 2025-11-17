package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.dominio.entidades.OpcionEncuesta;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioOpcionEncuestaImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioOpcionEncuestaTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioOpcionEncuesta repositorio;

    @BeforeEach
    public void init() {
        this.repositorio = new RepositorioOpcionEncuestaImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaGuardarUnaOpcion() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);
        Encuesta encuesta = crearEncuesta(c);

        OpcionEncuesta opcion = crearOpcion(encuesta, "Sí");

        OpcionEncuesta guardada = repositorio.guardar(opcion);

        assertThat(guardada.getId(), is(notNullValue()));
        assertThat(guardada.getTexto(), is("Sí"));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerOpcionPorId() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);
        Encuesta encuesta = crearEncuesta(c);

        OpcionEncuesta opcion = crearOpcion(encuesta, "No");
        repositorio.guardar(opcion);

        OpcionEncuesta obtenida = repositorio.obtenerPorId(opcion.getId());

        assertThat(obtenida, is(notNullValue()));
        assertThat(obtenida.getId(), is(equalTo(opcion.getId())));
        assertThat(obtenida.getTexto(), is("No"));
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerPorIdInexistente_DeberiaRetornarNull() {

        OpcionEncuesta obtenida = repositorio.obtenerPorId(999L);

        assertThat(obtenida, is(nullValue()));
    }

    private Usuario crearUsuario() {
        Usuario u = new Usuario();
        u.setNombre("prueba");
        sessionFactory.getCurrentSession().save(u);
        return u;
    }

    private Comunidad crearComunidad(Usuario u) {
        Comunidad c = new Comunidad();
        c.setNombre("Comu Test");
        c.setUsuarioCreador(u);
        sessionFactory.getCurrentSession().save(c);
        return c;
    }

    private Encuesta crearEncuesta(Comunidad c) {
        Encuesta e = new Encuesta();
        e.setPregunta("probando");
        e.setActiva(true);
        e.setComunidad(c);
        sessionFactory.getCurrentSession().save(e);
        return e;
    }

    private OpcionEncuesta crearOpcion(Encuesta e, String texto) {
        OpcionEncuesta o = new OpcionEncuesta();
        o.setTexto(texto);
        o.setEncuesta(e);
        return o;
    }

}
