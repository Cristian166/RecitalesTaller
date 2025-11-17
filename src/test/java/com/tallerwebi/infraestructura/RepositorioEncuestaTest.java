package com.tallerwebi.infraestructura;

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
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioEncuestaImpl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioEncuestaTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioEncuesta repositorio;

    @BeforeEach
    public void init() {
        this.repositorio = new RepositorioEncuestaImpl(sessionFactory);
    }

    private Usuario crearUsuario() {
        Usuario u = new Usuario();
        u.setNombre("Prueba");
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

    private Encuesta crearEncuesta(Comunidad c, boolean activa) {
        Encuesta e = new Encuesta();
        e.setPregunta("probando");
        e.setActiva(activa);
        e.setComunidad(c);
        return e;
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaGuardarUnaEncuesta() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);
        Encuesta encuesta = crearEncuesta(c, true);

        Boolean guardado = repositorio.guardar(encuesta);

        assertThat(guardado, is(true));
        assertThat(encuesta.getId(), is(notNullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerEncuestaPorId() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);
        Encuesta encuesta = crearEncuesta(c, true);

        repositorio.guardar(encuesta);

        Encuesta obtenida = repositorio.obtenerPorId(encuesta.getId());

        assertThat(obtenida, is(notNullValue()));
        assertThat(obtenida.getId(), is(equalTo(encuesta.getId())));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerEncuestaActivaDeUnaComunidad() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);

        Encuesta e1 = crearEncuesta(c, false);
        repositorio.guardar(e1);

        Encuesta e2 = crearEncuesta(c, true);
        repositorio.guardar(e2);

        Encuesta activa = repositorio.obtenerEncuestaActiva(c.getId());

        assertThat(activa, is(notNullValue()));
        assertThat(activa.isActiva(), is(true));
        assertThat(activa.getId(), is(equalTo(e2.getId())));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaEliminarEncuesta() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);
        Encuesta encuesta = crearEncuesta(c, true);

        repositorio.guardar(encuesta);

        Boolean eliminado = repositorio.eliminar(encuesta.getId());

        Encuesta obtenida = repositorio.obtenerPorId(encuesta.getId());

        assertThat(eliminado, is(true));
        assertThat(obtenida, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void eliminarEncuestaInexistente_DeberiaRetornarFalse() {

        Boolean eliminado = repositorio.eliminar(999L);

        assertThat(eliminado, is(false));
    }

}
