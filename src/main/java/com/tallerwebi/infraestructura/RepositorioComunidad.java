package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Comunidad;

import java.util.Set;

public interface RepositorioComunidad {

    //metodos cortos
    Comunidad guardar(Comunidad comunidad);
    Set<Comunidad> obtenerMisComunidades();
    Comunidad obtenerPorId(Long id);
    void borrarComunidad(Long id);
}
