package com.tallerwebi.infraestructura.repositorioImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.OpcionEncuesta;
import com.tallerwebi.infraestructura.RepositorioOpcionEncuesta;

@Repository("repositorioOpcionEncuesta")
public class RepositorioOpcionEncuestaImpl implements RepositorioOpcionEncuesta {

    private final SessionFactory sessionFactory;

    public RepositorioOpcionEncuestaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public OpcionEncuesta guardar(OpcionEncuesta opcion) {
        getSession().saveOrUpdate(opcion);
        return opcion;
    }

    @Override
    public OpcionEncuesta obtenerPorId(Long id) {
        return getSession().get(OpcionEncuesta.class, id);
    }

}
