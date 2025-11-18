package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;
import java.util.Set;

public interface RepositorioComunidad {

    //metodos cortos
    Comunidad guardarUnaComunidad(Comunidad comunidad);
    Comunidad obtenerComunidadPorId(Long id);
    void borrarComunidad(Long id);
    Set<Comunidad> obtenerComunidadesUnidas(Long usuarioId);
    Set<Comunidad> obtenerComunidadesSugeridas(Long usuarioId);
    void abandonarComunidad(Usuario usuario, Long comunidadId);
    Set<Usuario> obtenerMiembros(Long comunidadId);
    long contarMiembrosDeComunidad(Long comunidadId);
    Comunidad obtenerComunidadPorNombre(String nombre);
    //buscador comunidad
    Set<Comunidad> buscarComunidadesPorNombreYNoUnidas(String nombre, Usuario usuario);
}
