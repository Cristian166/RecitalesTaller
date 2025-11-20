package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface ServicioInsignia {

    boolean asignarInsignia(Usuario usuario, Insignia insignia);

    List<Insignia> obtenerInsigniasDeUsuario(Usuario usuario);

    boolean asignarInsigniaFinalSiCorresponde(Usuario usuario);

}