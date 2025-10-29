package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioComunidadImpl implements ServicioComunidad {

    private List<Comunidad> comunidades = new ArrayList<>();

    //prueba
    public ServicioComunidadImpl() {
        Comunidad prueba = new Comunidad();
        prueba.setId(1L);
        prueba.setNombre("Comunidad de prueba");
        prueba.setDescripcion("Descripción de prueba");
        prueba.setMiembros(new ArrayList<>());
        prueba.setPublicaciones(new ArrayList<>());

        comunidades.add(prueba);
    }

    @Override
    public List<Comunidad> listarTodasLasComunidades() {
        return comunidades;
    }

    @Override
    public Comunidad obtenerComunidad(Long id) {
        return comunidades.stream()
                .filter( c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void unirseAComunidad(Usuario usuario, Long comunidadId) {
        Comunidad c = obtenerComunidad(comunidadId);
        if(c != null && !c.getMiembros().contains(usuario)) {
            c.getMiembros().add(usuario);
        }
    }

    @Override
    public void abandonarComunidad(Usuario usuario, Long comunidadId) {
        Comunidad c = obtenerComunidad(comunidadId);
        if(c != null){
            c.getMiembros().remove(usuario);
        }

    }
}
