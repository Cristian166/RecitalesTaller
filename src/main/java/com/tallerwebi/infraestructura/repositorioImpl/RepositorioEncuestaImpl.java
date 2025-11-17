package com.tallerwebi.infraestructura.repositorioImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.infraestructura.RepositorioEncuesta;

@Repository("repositorioEncuesta")
public class RepositorioEncuestaImpl implements RepositorioEncuesta {

    private final SessionFactory sessionFactory;

    public RepositorioEncuestaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Boolean guardar(Encuesta encuesta) {
        try {
            getSession().saveOrUpdate(encuesta);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Encuesta obtenerEncuestaActiva(Long comunidadId) {
        return sessionFactory.getCurrentSession()
                .createQuery(
                        "SELECT e FROM Encuesta e LEFT JOIN FETCH e.opciones WHERE e.comunidad.id = :id AND e.activa = true",
                        Encuesta.class)
                .setParameter("id", comunidadId)
                .uniqueResult();
    }

    @Override
    public Encuesta obtenerPorId(Long id) {
        return getSession().get(Encuesta.class, id);
    }

    @Override
    public Boolean eliminar(Long id) {

        try {
            Encuesta encuesta = obtenerPorId(id);
            if (encuesta == null) {
                return false;
            }
            getSession().delete(encuesta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
