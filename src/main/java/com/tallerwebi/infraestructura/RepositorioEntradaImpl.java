package com.tallerwebi.infraestructura;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Entrada;

@Repository("repositorioEntrada")
public class RepositorioEntradaImpl implements RepositorioEntrada {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEntradaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarEntrada(Entrada entrada) {
        sessionFactory.getCurrentSession().saveOrUpdate(entrada);
    }

    @Override
    public void eliminarPorId(Long id) {
        Entrada entrada = sessionFactory.getCurrentSession().get(Entrada.class, id);
        if (entrada != null) {
            sessionFactory.getCurrentSession().delete(entrada);
        }
    }

    @Override
    public List<Entrada> obtenerEntradas() {
        return sessionFactory.getCurrentSession()
            .createQuery("FROM Entrada", Entrada.class)
            .list();
    }

    @Override
    public Entrada buscarPorId(Long id) {
        Entrada entrada = sessionFactory.getCurrentSession().get(Entrada.class, id);
        return entrada;
    }


    
}
