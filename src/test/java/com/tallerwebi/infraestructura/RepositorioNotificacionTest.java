package com.tallerwebi.infraestructura;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioNotificacionImpl;

import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioNotificacionTest {

     @Autowired
    private SessionFactory sessionFactory;
    private RepositorioNotificacion repositorioNotificacion;

    @BeforeEach
    public void init() {
        this.repositorioNotificacion = new RepositorioNotificacionImpl(sessionFactory);
    }


    @Test
    @Transactional
    @Rollback
    public void debePoderAgregar1NotificacionYAsignarlaAUnUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario);

        Notificacion notificacion = new Notificacion();
        notificacion.setNombreNotificacion("Nueva entrada");
        notificacion.setDescripcionNotificacion("Se ha creado una nueva entrada");
        notificacion.setLink("/mis-entradas");

        repositorioNotificacion.agregarNotificacion(usuario, notificacion);
        List<Notificacion> notificaciones = repositorioNotificacion.obtenerNotificacionesPorUsuario(usuario);

        assertEquals(1, notificaciones.size());

    }
    
    @Test
    @Transactional
    @Rollback
    public void debePoderObtenerLasNotificacionesQueTieneCadaUsuario(){

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Alan");
        sessionFactory.getCurrentSession().save(usuario2);

        Notificacion notificacion = new Notificacion();
        Notificacion notificacion2 = new Notificacion();
        Notificacion notificacion3 = new Notificacion();

        repositorioNotificacion.agregarNotificacion(usuario1, notificacion);
        repositorioNotificacion.agregarNotificacion(usuario1, notificacion2);

        repositorioNotificacion.agregarNotificacion(usuario2, notificacion3);

        List<Notificacion> notificacionesUsuario1 = repositorioNotificacion.obtenerNotificacionesPorUsuario(usuario1);
        List<Notificacion> notificacionesUsuario2 = repositorioNotificacion.obtenerNotificacionesPorUsuario(usuario2);

        assertEquals(2, notificacionesUsuario1.size());
        assertEquals(1, notificacionesUsuario2.size());
    }
}