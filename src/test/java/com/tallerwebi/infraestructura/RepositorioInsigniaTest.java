package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.Insignia;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.persistence.Query;
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

        Insignia insignia = new Insignia();
        insignia.setId(1L);
        insignia.setNombre("Insignia");
        insignia.setDescripcion("descripcion");
        insignia.setImagen("imagen");

        repositorio.guardar(insignia);

        String hql = "FROM Insignia WHERE id = :id";
        Query query = this.sessionfactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", 1L);

        Insignia insigniaObtenida = (Insignia)query.getSingleResult();

        assertThat(insigniaObtenida, is(equalTo(insignia)));
    }

    
}
