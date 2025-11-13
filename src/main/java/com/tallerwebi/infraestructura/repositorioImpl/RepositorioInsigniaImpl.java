package com.tallerwebi.infraestructura.repositorioImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.infraestructura.RepositorioInsignia;

@Repository("repositorioInsignia")
public class RepositorioInsigniaImpl implements RepositorioInsignia {

    private SessionFactory sessionfactory;

    @Autowired
    public RepositorioInsigniaImpl(SessionFactory sessionfactory) {
        this.sessionfactory = sessionfactory;
    }

    @Override
    public Long guardar(Insignia insignia) {
        return (Long) this.sessionfactory.getCurrentSession().save(insignia);
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
