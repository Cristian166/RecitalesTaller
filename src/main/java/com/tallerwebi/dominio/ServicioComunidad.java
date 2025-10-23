package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface ServicioComunidad {

    List<Comunidad> listarTodasLasComunidades();
    Comunidad obtenerComunidad(Long id);
    void unirseAComunidad(Usuario usuario,Long comunidadId);
    void abandonarComunidad(Usuario usuario, Long comunidadId);

}
