package com.tallerwebi.dominio.serviciosimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioNotificacion;

@Service
@Transactional
public class ServicioNotificacionImpl implements ServicioNotificacion {

    @Autowired
    private RepositorioNotificacion repositorioNotificacion;

    public ServicioNotificacionImpl(RepositorioNotificacion repositorioNotificacion){
        this.repositorioNotificacion = repositorioNotificacion;
    }

    @Override
    public List<Notificacion> obtenerNotificacionesPorUsuario(Usuario usuario) {
      return  repositorioNotificacion.obtenerNotificacionesPorUsuario(usuario);
    }

    @Override
    public void agregarNotificacion(Usuario usuario, Notificacion notificiacion) {
        repositorioNotificacion.agregarNotificacion(usuario, notificiacion);
    }

    @Override
    public void marcarTodasComoLeidas(Usuario usuario) {
      List<Notificacion> notificaciones = repositorioNotificacion.obtenerNotificacionesPorUsuario(usuario);
        for (Notificacion notificacion : notificaciones) {
            notificacion.setVista(true);
        }
    }

    
    
}
