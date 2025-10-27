package com.tallerwebi.infraestructura;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.interfacesrepo.RepositorioBandaInterface;
import com.tallerwebi.dominio.interfacesrepo.RepositorioRecitalInterface;

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
    public List<Artista> obtenerTodasLasBandas() {
        return sessionFactory.
                getCurrentSession().
                createQuery("FROM Banda", Artista.class).list();
    }
    @Override
    public Artista buscarPorId(Long bandaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }
    @Override
    public void guardar(Artista banda) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }
    
}
