package com.tallerwebi.infraestructura;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.entidades.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPublicacion")
public class RepositorioPublicacionImpl implements RepositorioPublicacion {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Publicacion guardar(Publicacion publicacion) {
        getCurrentSession().saveOrUpdate(publicacion);
        return publicacion;
    }

    @Override
    public List<Publicacion> obtenerPorComunidad(Long comunidadId){
        return getCurrentSession()
                .createQuery("FROM Publicacion p WHERE p.comunidad.id = :id ORDER BY p.fechaCreacion DESC", Publicacion.class)
                .setParameter("id", comunidadId)
                .list();
    }

    @Override
    public Publicacion obtenerPorId(Long id) {
        return getCurrentSession().get(Publicacion.class, id);
    }

    @Override
    public void eliminar(Long id) {
        Publicacion publicacion = obtenerPorId(id);
        if(publicacion != null){
            getCurrentSession().delete(publicacion);
        }
    }
}
