package com.tallerwebi.infraestructura.repositorioImpl;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.infraestructura.RepositorioComunidad;

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
    public Comunidad guardarUnaComunidad(Comunidad comunidad){
        getCurrentSession().saveOrUpdate(comunidad);
        return comunidad;
    }

    @Override
    public Comunidad obtenerComunidadPorId(Long id) {

        return getCurrentSession().get(Comunidad.class, id);
    }

    @Override
    public void borrarComunidad(Long id){
        Comunidad comunidad = obtenerComunidadPorId(id);
        if(comunidad != null){
            getCurrentSession().delete(comunidad);
        }
    }

    @Override
    public Set<Comunidad> obtenerComunidadesUnidas(Long usuarioId) {
        List<Comunidad> comunidadList = getCurrentSession()
                .createQuery("SELECT c FROM Comunidad c JOIN c.usuarios u WHERE u.id = :usuarioId", Comunidad.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        return new HashSet<>(comunidadList);
    }

    @Override
    public Set<Comunidad> obtenerComunidadesSugeridas(Long usuarioId) {
        List<Comunidad> comunidadList = getCurrentSession()
                .createQuery("SELECT c FROM Comunidad c WHERE c.id NOT IN " +
                        "(SELECT c2.id FROM Comunidad c2 JOIN c2.usuarios u WHERE u.id = :usuarioId)", Comunidad.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        return new HashSet<>(comunidadList);
    }

}
