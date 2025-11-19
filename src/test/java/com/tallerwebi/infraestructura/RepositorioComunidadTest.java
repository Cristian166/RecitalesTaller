package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

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
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioComunidadImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioComunidadTest {
    
    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioComunidad repositorioComunidad;

    @BeforeEach
    public void init(){
        this.repositorioComunidad = new RepositorioComunidadImpl(sessionFactory);
    }

    private Usuario crearUsuario(String nombre){
        Usuario u = new Usuario();
        u.setNombre(nombre);
        sessionFactory.getCurrentSession().save(u);
        return u;
    }

    private Comunidad crearComunidad(String nombre, Usuario u) {
        Comunidad c = new Comunidad();
        c.setNombre(nombre);
        c.setUsuarioCreador(u);
        c.getUsuarios().add(u);
        sessionFactory.getCurrentSession().saveOrUpdate(u);
        sessionFactory.getCurrentSession().save(c);
        return c;
    }

    @Test
    @Transactional
    @Rollback
    public void debeGuardarUnaComunidad(){

        Usuario u = crearUsuario("Nahuel");
        Comunidad c = crearComunidad("Comunidad Test",u);

        Comunidad comunidadGuardada = repositorioComunidad.guardarUnaComunidad(c);

        assertThat(comunidadGuardada.getId(), is(notNullValue()));
        assertThat(comunidadGuardada.getNombre(),is("Comunidad Test"));
    }

    @Test
    @Transactional
    @Rollback
    public void debeObtenerUnaComunidadPorId(){
        Usuario u = crearUsuario("Nahuel");
        Comunidad c = crearComunidad("Comunidad Test",u);

        Comunidad comunidadObtenida = repositorioComunidad.obtenerComunidadPorId(c.getId());

        assertThat(comunidadObtenida,is(notNullValue()));
        assertThat(comunidadObtenida.getId(),is(c.getId()));
        assertThat(comunidadObtenida.getNombre(),is("Comunidad Test"));
    }

    @Test
    @Transactional
    @Rollback
    public void debePoderBorrarUnaComunidad(){
        
        Usuario u = crearUsuario("Nahuel");
        Comunidad c = crearComunidad("Comunidad Test",u);

        Comunidad comunidadAntesDeBorrar = repositorioComunidad.obtenerComunidadPorId(c.getId());

        assertThat(comunidadAntesDeBorrar,is(notNullValue()));

        repositorioComunidad.borrarComunidad(c.getId());

        Comunidad comunidadDespuesDeBorrar = repositorioComunidad.obtenerComunidadPorId(c.getId());

        assertThat(comunidadDespuesDeBorrar,is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerComunidadesUnidas(){
        Usuario usuario1 = crearUsuario("usuario 1");
        Usuario usuario2 = crearUsuario("usuario 2");

        Comunidad comunidad1 = crearComunidad("Comunidad 1", usuario1);
        Comunidad comunidad2 = crearComunidad("Comunidad 2", usuario1);
        Comunidad comunidad3 = crearComunidad("Comunidad 3", usuario2);

        Set<Comunidad> comunidadesUnidas = repositorioComunidad.obtenerComunidadesUnidas(usuario1.getId());

        assertThat(comunidadesUnidas,hasSize(2));
        assertThat(comunidadesUnidas,hasItem(comunidad1));
        assertThat(comunidadesUnidas,hasItem(comunidad2));

        assertThat(comunidadesUnidas,not(hasItem(comunidad3)));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerComunidadesSugeridas(){
        Usuario usuario1 = crearUsuario("Usuario Prueba");
        Comunidad comunidad1 = crearComunidad("Comunidad 1", usuario1);
        Comunidad comunidad2 = crearComunidad("Comunidad 2", usuario1); 
        Comunidad comunidad3 = crearComunidad("Comunidad 3", new Usuario());
        Comunidad comunidad4 = crearComunidad("Comunidad 4", new Usuario()); 
    
        Set<Comunidad> comunidadesSugeridas = repositorioComunidad.obtenerComunidadesSugeridas(usuario1.getId());
        //verifica que no sea nulo
        assertNotNull(comunidadesSugeridas);

        assertThat(comunidadesSugeridas,hasSize(2));
        assertThat(comunidadesSugeridas,hasSize(2));
        // Verifica que la comunidad3 esté en las comunidades sugeridas
        assertThat(comunidadesSugeridas,hasItem(comunidad3));
        // Verifica que la comunidad4 esté en las comunidades sugeridas
        assertThat(comunidadesSugeridas,hasItem(comunidad4));
        
        // Verifica que las comunidades 1 y 2 NO estén en las comunidades sugeridas
        assertThat(comunidadesSugeridas, not(hasItem(comunidad1)));
        assertThat(comunidadesSugeridas, not(hasItem(comunidad2)));
    }

    @Test
    @Transactional
    @Rollback
    public void debeAbandonarUnaComunidad(){
        
        Usuario usuario = crearUsuario("Fulano");
        Comunidad comunidad = crearComunidad("Comunidad Test", usuario);

        Set<Usuario> usuarioAntesDeAbandonar = comunidad.getUsuarios();
        assertThat(usuarioAntesDeAbandonar,hasSize(1));

        repositorioComunidad.abandonarComunidad(usuario,comunidad.getId());

        Set<Usuario> usuarioDespuesDeAbandonar = comunidad.getUsuarios();

        assertThat(usuarioDespuesDeAbandonar, hasSize(0));
    }

    @Test
    @Transactional
    @Rollback
    public void debeObtenerMiembrosDeComunidad(){
        
        Usuario usuario1 = crearUsuario("Fulano");
        Usuario usuario2 = crearUsuario("Nahuel");

        Comunidad comunidad = crearComunidad("Comunidad Test", usuario1);
        comunidad.getUsuarios().add(usuario2);

        Set<Usuario> miembrosComunidad = repositorioComunidad.obtenerMiembros(comunidad.getId());

        assertNotNull(miembrosComunidad);
        assertThat(miembrosComunidad,hasSize(2));
        assertThat(miembrosComunidad,hasItem(usuario1));
        assertThat(miembrosComunidad,hasItem(usuario2));
    }

    @Test
    @Transactional
    @Rollback
    public void debeContarMiembrosDeComunidad(){
        
        Usuario usuario1 = crearUsuario("Usuario 1");
        Usuario usuario2 = crearUsuario("Usuario 2");

        Comunidad comunidad = crearComunidad("Comunidad Test",usuario1);
        comunidad.getUsuarios().add(usuario2);

        sessionFactory.getCurrentSession().update(comunidad);

        long cantidadMiembros = repositorioComunidad.contarMiembrosDeComunidad(comunidad.getId());

        assertThat(cantidadMiembros,is(2L));
    }

    @Test
    @Transactional
    @Rollback
    public void debeObtenerComunidadPorNombre(){

        Usuario usuario = crearUsuario("Usuario 1");
        Comunidad comunidad = crearComunidad("Comunidad Test",usuario);
        
        Comunidad comunidadObtenida = repositorioComunidad.obtenerComunidadPorNombre("Comunidad Test");
        assertNotNull(comunidadObtenida);
        assertThat(comunidadObtenida.getNombre(), is("Comunidad Test"));
        assertThat(comunidadObtenida.getId(),is(comunidad.getId()));
    }

}
