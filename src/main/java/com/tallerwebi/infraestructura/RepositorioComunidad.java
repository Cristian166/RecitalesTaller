package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Comunidad;

import java.util.Set;

public interface RepositorioComunidad {

    //metodos cortos
    Comunidad guardarUnaComunidad(Comunidad comunidad);
    Comunidad obtenerComunidadPorId(Long id);
    void borrarComunidad(Long id);
    Set<Comunidad> obtenerComunidadesUnidas(Long usuarioId);
    Set<Comunidad> obtenerComunidadesSugeridas(Long usuarioId);
}
