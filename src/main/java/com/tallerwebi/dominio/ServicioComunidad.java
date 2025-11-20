package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.Map;
import java.util.Set;

public interface ServicioComunidad {

    Set<Comunidad> listarComunidadesUnidas(Long usuarioId);
    Set<Comunidad> listarComunidadesSugeridas(Long usuarioId);
    Comunidad obtenerComunidad(Long id);

    void unirseAComunidad(Usuario usuario, Long comunidadId);

    void abandonarComunidad(Usuario usuario, Long comunidadId);

    Comunidad crearComunidad(Comunidad comunidad, Usuario usuario);

    void eliminarComunidad(Long id);
    long contarMiembrosComunidad(Long comunidadId);
    boolean existeComunidadPorNombre(String nombre);

    void destacarPublicacion(Long idComunidad, Long idPublicacion, Usuario actual);

    void quitarDestacado(Long idComunidad, Long idPublicacion, Usuario actual);
    Set<Map<String, Object>> buscarComunidadesPorNombre(String nombre, Usuario usuario);
    void eliminarPublicacion(Long idComunidad, Long idPublicacion, Usuario actual);
}
