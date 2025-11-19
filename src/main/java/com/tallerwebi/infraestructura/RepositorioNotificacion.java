package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;    

public interface RepositorioNotificacion {

    List<Notificacion> obtenerNotificacionesPorUsuario(Usuario usuario);

    void agregarNotificacion(Usuario usuario, Notificacion notificiacion);

}
