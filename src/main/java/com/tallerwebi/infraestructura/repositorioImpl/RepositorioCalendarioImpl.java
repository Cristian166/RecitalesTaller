package com.tallerwebi.infraestructura.repositorioImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioCalendario;

@Repository("repositorioCalendario")
public class RepositorioCalendarioImpl implements RepositorioCalendario{

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCalendarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Entrada> obtenerEventos(Usuario usuario) {
       return this.sessionFactory.getCurrentSession()
        .createQuery("FROM Entrada WHERE usuario.id = :idUsuario ORDER BY id  DESC")
        .setParameter("idUsuario", usuario.getId())
        .list();
    }

    
}
