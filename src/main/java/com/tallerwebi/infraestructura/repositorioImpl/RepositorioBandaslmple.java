package com.tallerwebi.infraestructura.repositorioImpl;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.interfacesrepo.RepositorioBandaInterface;

import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class RepositorioBandaslmple implements RepositorioBandaInterface {

    private SessionFactory sessionFactory;


    @Autowired
    public RepositorioBandaslmple(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;}



    @Override
    public List<Artista> obtenerTodasLasBandas() {
        return sessionFactory.
                getCurrentSession().
                createQuery("FROM Banda", Artista.class).list();
    }

    public Artista buscarPorId(Long bandaId) {
        return this.sessionFactory.getCurrentSession().get(Artista.class, bandaId);
    }

    public void guardar(Artista banda) {
        this.sessionFactory.getCurrentSession().save(banda);
    }
}
