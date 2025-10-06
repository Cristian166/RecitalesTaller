package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioInsignia {

    boolean asignarInsignia(Usuario usuario, Insignia insignia);
    List<Insignia> obtenerInsigniasDeUsuario(Usuario usuario);

}