package com.tallerwebi.infraestructura.repositorioImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Preferencia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioPerfil;

@Repository("repositorioPerfil")
public class RepositorioPerfilImpl implements RepositorioPerfil {

    private SessionFactory sessionfactory;  

     @Autowired
    public RepositorioPerfilImpl(SessionFactory sessionfactory) {
        this.sessionfactory = sessionfactory;
    }

    @Override
    public List<Preferencia> obtenerPreferenciaExistentes() {
        return sessionfactory.getCurrentSession().
        createQuery("FROM Preferencia", Preferencia.class)
        .list();
    }

    @Override
    public List<String> obtenerNombresDePreferenciasPorTipo(String tipo) {
       return sessionfactory.getCurrentSession()
        .createQuery("SELECT p.nombre FROM Preferencia p WHERE p.tipo = :tipo", String.class)
        .setParameter("tipo", tipo)
        .list();
    }

    @Override
    public PreferenciaUsuario obtenerPreferenciasPorUsuario(Usuario usuario) {
        return (PreferenciaUsuario) this.sessionfactory.getCurrentSession()
            .createQuery("FROM PreferenciaUsuario WHERE usuario.id = :idUsuario")
            .setParameter("idUsuario", usuario.getId())
            .uniqueResult();
        }
    

    @Override
    public void guardarPreferenciasPorUsuario(Long idUsuario, List<String> generosSeleccionados,
            List<String> artistasSeleccionados, List<String> regionesSeleccionadas,
            List<String> epocasSeleccionadas) {

        // Obtener el usuario
        Usuario usuario = sessionfactory.getCurrentSession().get(Usuario.class, idUsuario);

        // Buscar si ya tiene preferencias
        PreferenciaUsuario preferenciasExistentesDelUsuario = obtenerPreferenciasPorUsuario(usuario);

        if (preferenciasExistentesDelUsuario == null) {
            // Crear nuevas
            PreferenciaUsuario nuevasPreferencias = new PreferenciaUsuario(usuario, generosSeleccionados,
                    artistasSeleccionados, regionesSeleccionadas, epocasSeleccionadas);
            sessionfactory.getCurrentSession().save(nuevasPreferencias);
        } else {
            // Actualizar existentes
            preferenciasExistentesDelUsuario.setGenerosSeleccionados(generosSeleccionados);
            preferenciasExistentesDelUsuario.setArtistasSeleccionados(artistasSeleccionados);
            preferenciasExistentesDelUsuario.setRegionesSeleccionadas(regionesSeleccionadas);
            preferenciasExistentesDelUsuario.setEpocasSeleccionadas(epocasSeleccionadas);
            sessionfactory.getCurrentSession().update(preferenciasExistentesDelUsuario);
        }
    }


    




}
