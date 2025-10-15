package com.tallerwebi.infraestructura;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Insignia;

@Repository("repositorioInsignia")
public class RepositorioInsigniaImpl implements RepositorioInsignia {

    private SessionFactory sessionfactory;

    @Autowired
    public RepositorioInsigniaImpl(SessionFactory sessionfactory) {
        this.sessionfactory = sessionfactory;
    }

    @Override
    public Boolean guardar(Insignia insignia) {
        return this.sessionfactory.getCurrentSession().save(insignia) != null;
    }

    @Override
    public Insignia obtenerPorId(Long id) {
        return this.sessionfactory.getCurrentSession().get(Insignia.class, id);
    }

    @Override
    public List<Insignia> obtenerTodas() {
        return this.sessionfactory.getCurrentSession().createQuery("FROM Insignia", Insignia.class).list();
    }

    @Override
    public void eliminar(Insignia insignia) {
        this.sessionfactory.getCurrentSession().delete(insignia);
    }

}
