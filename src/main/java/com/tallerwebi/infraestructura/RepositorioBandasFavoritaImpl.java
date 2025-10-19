package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.entidades.Banda;
import com.tallerwebi.dominio.entidades.BandaFavorita;
import com.tallerwebi.dominio.interfacesrepo.RepositorioBandasFavoritaInterface;

import java.util.List;

@Repository
public class RepositorioBandasFavoritaImpl implements RepositorioBandasFavoritaInterface {
        private SessionFactory sessionFactory;

        @Autowired
        public RepositorioBandasFavoritaImpl(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

    @Override
    public void guardar(BandaFavorita bandaFavorita) {
        this.sessionFactory.getCurrentSession().save(bandaFavorita);
    }

    @Override
    public List<Banda> obtenerSoloBandasFavoritas(Long usuarioId) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT bf.banda FROM BandaFavorita bf WHERE bf.usuario.id = :usuarioId", Banda.class)
                .setParameter("usuarioId", usuarioId)
                .list();
    }


}
