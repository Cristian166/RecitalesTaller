package com.tallerwebi.infraestructura.repositorioImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioNotificacion;

@Repository("repositorioNotificacion")
public class RepositorioNotificacionImpl implements RepositorioNotificacion{

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioNotificacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Notificacion> obtenerNotificacionesPorUsuario(Usuario usuario) {
       return this.sessionFactory.getCurrentSession()
        .createQuery("FROM Notificacion WHERE usuario.id = :idUsuario ORDER BY id  DESC")
        .setParameter("idUsuario", usuario.getId())
        .list();
    }

    @Override
    public void agregarNotificacion(Usuario usuario, Notificacion notificiacion) {
        notificiacion.setUsuario(usuario);
        sessionFactory.getCurrentSession().saveOrUpdate(notificiacion);
    }
    
}
