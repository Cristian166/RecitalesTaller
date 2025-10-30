package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Comunidad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("repositorioComunidad")
public class RepositorioComunidadImpl implements RepositorioComunidad{

    private SessionFactory sessionFactory;

    public RepositorioComunidadImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    //metodo y dentro sessionFactory.getCurrentSession().*VERBO A HACER*
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Comunidad guardar(Comunidad comunidad){
        getCurrentSession().saveOrUpdate(comunidad);
        return comunidad;
    }

    @Override
    public Set<Comunidad> obtenerMisComunidades(){
        List<Comunidad> comunidadList = getCurrentSession().createQuery("FROM Comunidad", Comunidad.class).list();
        return new HashSet<>(comunidadList);
    }

    @Override
    public Comunidad obtenerPorId(Long id) {

        return getCurrentSession().get(Comunidad.class, id);
    }

    @Override
    public void borrarComunidad(Long id){
        Comunidad comunidad = obtenerPorId(id);
        if(comunidad != null){
            getCurrentSession().delete(comunidad);
        }
    }

}
