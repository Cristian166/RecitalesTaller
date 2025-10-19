package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Recital;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.interfacesrepo.RepositorioRecitalInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository ("Repositorio Recital")

public class RepositorioRecitalImple implements RepositorioRecitalInterface {

    // me conecto a la base de datos
    private SessionFactory sessionFactory;

    // inicializo - constructor
    @Autowired
    public RepositorioRecitalImple(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarRecital(Recital recital) {
        sessionFactory.getCurrentSession().save(recital);
    }

    @Override
    public void borrarRecital(Recital recital) {
        sessionFactory.getCurrentSession().delete(recital);
    }

    @Override
    public Recital buscarRecitalPorIdRecital(Long id) {
       return sessionFactory.getCurrentSession().get(Recital.class, id);
    }

    @Override
    public List<Recital> obtenerRecitalesDeUsuario(Usuario usuario) {
        String hql = "from Recital where usuario=:usuario";
        return sessionFactory.getCurrentSession().createQuery(hql).setParameter("usuario", usuario).list();
    }
}
