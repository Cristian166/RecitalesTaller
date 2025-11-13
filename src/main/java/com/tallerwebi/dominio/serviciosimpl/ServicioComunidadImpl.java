package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

import com.tallerwebi.infraestructura.RepositorioComunidad;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ServicioComunidadImpl implements ServicioComunidad {

    private final RepositorioComunidad repositorioComunidad;

    public ServicioComunidadImpl(RepositorioComunidad repositorioComunidad) {
        this.repositorioComunidad = repositorioComunidad;
    }

    @Override
    @Transactional
    public Set<Comunidad> listarComunidadesUnidas(Long usuarioId){
        return repositorioComunidad.obtenerComunidadesUnidas(usuarioId);
    }

    @Override
    @Transactional
    public Set<Comunidad> listarComunidadesSugeridas(Long usuarioId){
        return repositorioComunidad.obtenerComunidadesSugeridas(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public Comunidad obtenerComunidad(Long id) {
        Comunidad comunidad = repositorioComunidad.obtenerComunidadPorId(id);
        System.out.println("Servicio comunidad obtenido: "+comunidad);
        return comunidad;
    }

    @Override
    @Transactional
    public void unirseAComunidad(Usuario usuario, Long comunidadId) {
        Comunidad comunidad = repositorioComunidad.obtenerComunidadPorId(comunidadId);

        if(comunidad != null && usuario != null){
            if (!comunidad.getUsuarios().contains(usuario)) {
                comunidad.getUsuarios().add(usuario);
                repositorioComunidad.guardarUnaComunidad(comunidad);
            }
        }
    }

    @Override
    @Transactional
    public void abandonarComunidad(Usuario usuario, Long comunidadId) {
        repositorioComunidad.abandonarComunidad(usuario, comunidadId);
    }

    @Override
    @Transactional
    public Comunidad crearComunidad(Comunidad comunidad) {
        return repositorioComunidad.guardarUnaComunidad(comunidad);
    }

    @Override
    @Transactional
    public void eliminarComunidad(Long id) {
         repositorioComunidad.borrarComunidad(id);
    }
}
