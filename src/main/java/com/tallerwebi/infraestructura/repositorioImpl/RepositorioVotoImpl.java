package com.tallerwebi.infraestructura.repositorioImpl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Voto;
import com.tallerwebi.infraestructura.RepositorioVoto;
import org.hibernate.Session;

@Repository("repositorioVoto")
public class RepositorioVotoImpl implements RepositorioVoto {

    private final SessionFactory sessionFactory;

    public RepositorioVotoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Voto guardar(Voto voto) {
        getSession().saveOrUpdate(voto);
        return voto;
    }

    @Override
    public Voto obtenerVotoDeUsuario(Long usuarioId, Long encuestaId) {
        return getSession()
                .createQuery("SELECT v FROM Voto v WHERE v.usuario.id = :uId AND v.opcion.encuesta.id = :eId",
                        Voto.class)
                .setParameter("uId", usuarioId)
                .setParameter("eId", encuestaId)
                .uniqueResult();
    }

    @Override
    public void eliminar(Voto voto) {
        getSession().delete(voto);
    }

}
