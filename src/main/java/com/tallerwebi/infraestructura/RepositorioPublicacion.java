package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Publicacion;

import java.util.List;

public interface RepositorioPublicacion {
    Boolean guardar(Publicacion publicacion);
    List<Publicacion> obtenerPorComunidad(Long comunidadId);
    Publicacion obtenerPorId(Long id);
    void eliminar(Long id);
}
