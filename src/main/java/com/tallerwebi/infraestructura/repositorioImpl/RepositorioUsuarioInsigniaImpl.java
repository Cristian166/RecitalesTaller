package com.tallerwebi.infraestructura.repositorioImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.UsuarioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

@Repository("repositorioUsuarioInsignia")
@Transactional
public class RepositorioUsuarioInsigniaImpl implements RepositorioUsuarioInsignia {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioInsigniaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean guardar(UsuarioInsignia usuarioInsignia) {
        try {
            this.sessionFactory.getCurrentSession().save(usuarioInsignia);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existe(Long usuarioId, Long insigniaId) {
        String hql = "FROM UsuarioInsignia ui WHERE ui.usuario.id = :usuarioId AND ui.insignia.id = :insigniaId";
        return this.sessionFactory.getCurrentSession()
                .createQuery(hql, UsuarioInsignia.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("insigniaId", insigniaId)
                .uniqueResult() != null;
    }

    @Override
    public List<UsuarioInsignia> obtenerPorUsuario(Usuario usuario) {
        String hql = "FROM UsuarioInsignia ui WHERE ui.usuario.id = :usuarioId";
        return this.sessionFactory.getCurrentSession()
                .createQuery(hql, UsuarioInsignia.class)
                .setParameter("usuarioId", usuario.getId())
                .list();
    }

}
