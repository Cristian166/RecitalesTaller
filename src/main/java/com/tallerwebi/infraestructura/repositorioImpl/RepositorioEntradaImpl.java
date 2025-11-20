package com.tallerwebi.infraestructura.repositorioImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioEntrada;

@Repository("repositorioEntrada")
public class RepositorioEntradaImpl implements RepositorioEntrada {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEntradaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarEntradaPorUsuario(Entrada entrada, Usuario usuario) {
        entrada.setUsuario(usuario);
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

    @Override
    public List<Entrada> obtenerEntradasPorUsuario(Usuario usuario) {
        return this.sessionFactory.getCurrentSession()
        .createQuery("FROM Entrada WHERE usuario.id = :idUsuario")
        .setParameter("idUsuario", usuario.getId())
        .list();
    }

    @Override
    public List<Entrada> obtenerEntradasValidadas() {
        return sessionFactory.getCurrentSession()
            .createQuery("FROM Entrada WHERE validada = true", Entrada.class)
            .list();
    }

    
}
