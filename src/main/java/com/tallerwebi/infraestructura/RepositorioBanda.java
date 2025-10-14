package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Banda;
import com.tallerwebi.dominio.RepositorioBandaInterface;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.RepositorioRecitalInterface;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class RepositorioBanda implements RepositorioBandaInterface {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Banda> obtenerTodasLasBandas() {
        return sessionFactory.
                getCurrentSession().
                createQuery("FROM Banda", Banda.class).list();
    }
}
