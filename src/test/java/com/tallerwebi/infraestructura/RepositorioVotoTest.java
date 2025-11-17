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
import com.tallerwebi.dominio.entidades.Voto;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioVotoImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioVotoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioVoto repositorio;

    @BeforeEach
    public void init() {
        this.repositorio = new RepositorioVotoImpl(sessionFactory);
    }

    private Usuario crearUsuario() {
        Usuario u = new Usuario();
        u.setNombre("probando");
        sessionFactory.getCurrentSession().save(u);
        return u;
    }

    private Comunidad crearComunidad(Usuario creador) {
        Comunidad c = new Comunidad();
        c.setNombre("Comunidad Test");
        c.setUsuarioCreador(creador);
        sessionFactory.getCurrentSession().save(c);
        return c;
    }

    private Encuesta crearEncuesta(Comunidad comunidad) {
        Encuesta e = new Encuesta();
        e.setPregunta("¿Frío o calor?");
        e.setActiva(true);
        e.setComunidad(comunidad);
        sessionFactory.getCurrentSession().save(e);
        return e;
    }

    private OpcionEncuesta crearOpcion(Encuesta encuesta, String texto) {
        OpcionEncuesta o = new OpcionEncuesta();
        o.setTexto(texto);
        o.setEncuesta(encuesta);
        sessionFactory.getCurrentSession().save(o);
        return o;
    }

    private Voto crearVoto(Usuario usuario, OpcionEncuesta opcion) {
        Voto v = new Voto();
        v.setUsuario(usuario);
        v.setOpcion(opcion);
        return v;
    }


    @Test
    @Transactional
    @Rollback
    public void deberiaGuardarUnVoto() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);
        Encuesta e = crearEncuesta(c);
        OpcionEncuesta o = crearOpcion(e, "test");

        Voto voto = crearVoto(u, o);

        Voto guardado = repositorio.guardar(voto);

        assertThat(guardado.getId(), is(notNullValue()));
        assertThat(guardado.getUsuario().getId(), is(u.getId()));
        assertThat(guardado.getOpcion().getId(), is(o.getId()));
    }


    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerVotoDeUsuarioParaEncuesta() {

        Usuario u = crearUsuario();

        Comunidad c = crearComunidad(u);
        Encuesta e = crearEncuesta(c);
        OpcionEncuesta o = crearOpcion(e, "test");

        Voto voto = crearVoto(u, o);
        repositorio.guardar(voto);

        Voto obtenido = repositorio.obtenerVotoDeUsuario(u.getId(), e.getId());

        assertThat(obtenido, is(notNullValue()));
        assertThat(obtenido.getUsuario().getId(), is(u.getId()));
        assertThat(obtenido.getOpcion().getEncuesta().getId(), is(e.getId()));
        assertThat(obtenido.getOpcion().getTexto(), is("test"));
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerVotoInexistente_DeberiaRetornarNull() {

        Voto obtenido = repositorio.obtenerVotoDeUsuario(999L, 999L);

        assertThat(obtenido, is(nullValue()));
    }


    @Test
    @Transactional
    @Rollback
    public void deberiaEliminarUnVoto() {

        Usuario u = crearUsuario();
        Comunidad c = crearComunidad(u);
        Encuesta e = crearEncuesta(c);
        OpcionEncuesta o = crearOpcion(e, "test");

        Voto voto = crearVoto(u, o);
        repositorio.guardar(voto);

        repositorio.eliminar(voto);

        Voto obtenido = repositorio.obtenerVotoDeUsuario(u.getId(), e.getId());

        assertThat(obtenido, is(nullValue()));
    }
}