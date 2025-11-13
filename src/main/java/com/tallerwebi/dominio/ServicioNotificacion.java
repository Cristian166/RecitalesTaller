package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;

public interface ServicioNotificacion {

    List<Notificacion> obtenerNotificacionesPorUsuario(Usuario usuario);

    void agregarNotificacion (Usuario usuario, Notificacion notificiacion); 

    void marcarTodasComoLeidas(Usuario usuario);


}
