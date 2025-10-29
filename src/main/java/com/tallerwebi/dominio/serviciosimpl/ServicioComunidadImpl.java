package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;

import com.tallerwebi.infraestructura.RepositorioComunidad;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ServicioComunidadImpl implements ServicioComunidad {

    private final RepositorioComunidad repositorioComunidad;

    public ServicioComunidadImpl(RepositorioComunidad repositorioComunidad) {
        this.repositorioComunidad = repositorioComunidad;
    }

    @Override
    public Set<Comunidad> listarTodasLasComunidades() {
        return repositorioComunidad.obtenerMisComunidades();
    }

    @Override
    public Comunidad obtenerComunidad(Long id) {
        return repositorioComunidad.obtenerPorId(id);
    }

    @Override
    @Transactional
    public void unirseAComunidad(Usuario usuario, Long comunidadId) {

        Comunidad comunidad = repositorioComunidad.obtenerPorId(comunidadId);

        if (comunidad != null && usuario != null) {

            if(!comunidad.getUsuarios().contains(usuario)){
                comunidad.getUsuarios().add(usuario);
                repositorioComunidad.guardar(comunidad);
            }

        }
    }

    @Override
    @Transactional
    public void abandonarComunidad(Usuario usuario, Long comunidadId) {

        Comunidad comunidad = repositorioComunidad.obtenerPorId(comunidadId);

        if (comunidad != null && usuario != null) {
            if(comunidad.getUsuarios().contains(usuario)){
                comunidad.getUsuarios().remove(usuario);
                repositorioComunidad.guardar(comunidad);
            }
        }
    }
}
