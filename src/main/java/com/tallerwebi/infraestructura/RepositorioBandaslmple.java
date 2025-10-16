package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Banda;
import com.tallerwebi.dominio.RepositorioBandaInterface;
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
    public List<Banda> obtenerTodasLasBandas() {
        return sessionFactory.
                getCurrentSession().
                createQuery("FROM Banda", Banda.class).list();
    }

    @Override
    public Banda buscarPorId(Long bandaId) {
        return this.sessionFactory.getCurrentSession().get(Banda.class, bandaId);
    }

    public void guardar(Banda banda) {
        this.sessionFactory.getCurrentSession().save(banda);
    }
}
