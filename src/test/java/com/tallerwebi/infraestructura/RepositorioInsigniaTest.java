package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasItems;

import java.util.List;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioInsigniaTest {

    @Autowired
    private SessionFactory sessionfactory;
    private RepositorioInsignia repositorio;

    @BeforeEach
    public void init(){
        this.repositorio = new RepositorioInsigniaImpl(sessionfactory);
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaGuardarUnaInsignia(){

        Insignia insignia = crearInsignia();

        Long idGuardado = repositorio.guardar(insignia);

        /*String hql = "FROM Insignia WHERE id = :id";
        Query query = this.sessionfactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", 1L);

        Insignia insigniaObtenida = (Insignia)query.getSingleResult();*/

        assertThat(idGuardado, is(notNullValue()));
    }

    
    @Test
    @Transactional
    @Rollback
    public void deberiaEliminarUnaInsignia(){

        Insignia insignia = crearInsignia();

        repositorio.guardar(insignia);

        repositorio.eliminar(insignia);

        Insignia insigniaObtenida = repositorio.obtenerPorId(1L);

        assertThat(insigniaObtenida, is(equalTo(null)));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerUnaInsigniaPorId(){

        Insignia insignia = crearInsignia();

        Long id = repositorio.guardar(insignia);

        Insignia insigniaObtenida = repositorio.obtenerPorId(id);

        assertThat(insigniaObtenida.getId(), is(equalTo(id)));
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerTodasLasInsignias(){

        Insignia insignia = crearInsignia();
        Insignia insignia2 = crearInsignia();
        insignia2.setNombre("Insignia2");

        repositorio.guardar(insignia);
        repositorio.guardar(insignia2);
        List<Insignia> todas = repositorio.obtenerTodas();

        assertThat(todas.size(), is(equalTo(2)));
        assertThat(todas.get(0).getNombre(), is(equalTo("Insignia")));
        assertThat(todas.get(1).getNombre(), is(equalTo("Insignia2")));
        assertThat(todas, hasItems(insignia, insignia2));
    }

    private Insignia crearInsignia() {
        Insignia insignia = new Insignia();
        insignia.setNombre("Insignia");
        insignia.setDescripcion("descripcion");
        insignia.setImagen("imagen");
        return insignia;
    }


}
