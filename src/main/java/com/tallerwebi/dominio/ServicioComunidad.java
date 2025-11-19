package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ServicioComunidad {

    public Set<Comunidad> listarComunidadesUnidas(Long usuarioId);

    public Set<Comunidad> listarComunidadesSugeridas(Long usuarioId);

    Comunidad obtenerComunidad(Long id);

    void unirseAComunidad(Usuario usuario, Long comunidadId);

    void abandonarComunidad(Usuario usuario, Long comunidadId);

    Comunidad crearComunidad(Comunidad comunidad, Usuario usuario);

    void eliminarComunidad(Long id);

    @Transactional
    long contarMiembrosComunidad(Long comunidadId);

    @Transactional(readOnly = true)
    boolean existeComunidadPorNombre(String nombre);

    void destacarPublicacion(Long idComunidad, Long idPublicacion, Usuario actual);

    void quitarDestacado(Long idComunidad, Long idPublicacion, Usuario actual);
}
