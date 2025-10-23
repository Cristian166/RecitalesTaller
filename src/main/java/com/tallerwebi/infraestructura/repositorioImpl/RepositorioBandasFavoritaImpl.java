package com.tallerwebi.infraestructura.repositorioImpl;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.BandaFavorita;
import com.tallerwebi.dominio.entidades.Usuario;
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
    public List<Artista> obtenerSoloBandasFavoritas(Long usuarioId) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT bf.banda FROM BandaFavorita bf WHERE bf.usuario.id = :usuarioId", Artista.class)
                .setParameter("usuarioId", usuarioId)
                .list();
    }

    public void agregarBandaFavorita(Usuario usuario, Artista banda) {
            BandaFavorita bandaFavorita = new BandaFavorita();
            bandaFavorita.setUsuario(usuario);
            bandaFavorita.setBanda(banda);
            
            this.sessionFactory.getCurrentSession().save(bandaFavorita);
    }

    public void eliminarBandaFavorita(Usuario usuario, Artista banda) {
     String hql = "DELETE FROM BandaFavorita WHERE usuario = :usuario AND banda = :banda";
    this.sessionFactory.getCurrentSession()
            .createQuery(hql)
            .setParameter("usuario", usuario)
            .setParameter("banda", banda)
            .executeUpdate();
    }

    public List<Artista> obtenerArtistasFavoritosDeUsuario(Usuario usuario) {
        // TODO Auto-generated method stub
           String hql = "SELECT bf.banda FROM BandaFavorita bf WHERE bf.usuario = :usuario";
            return this.sessionFactory.getCurrentSession()
            .createQuery(hql, Artista.class)
            .setParameter("usuario", usuario)
            .getResultList();
    }


}
