package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.Set;

public interface ServicioComunidad {

    public Set<Comunidad> listarComunidadesUnidas(Long usuarioId);
    public Set<Comunidad> listarComunidadesSugeridas(Long usuarioId);
    Comunidad obtenerComunidad(Long id);
    void unirseAComunidad(Usuario usuario, Long comunidadId);
    void abandonarComunidad(Usuario usuario, Long comunidadId);
    Comunidad crearComunidad(Comunidad comunidad, Usuario usuario);
    void eliminarComunidad(Long id);

}
