package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Publicacion;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioPublicacionImpl implements ServicioPublicacion {

    private Map<Long, List<Publicacion>> publicacionesPorComunidad = new HashMap<>();

    @Override
    public void crearPublicacion(Publicacion publicacion, Long comunidadId) {
        publicacionesPorComunidad.putIfAbsent(comunidadId, new ArrayList<>());

        publicacionesPorComunidad.get(comunidadId).add(publicacion);
    }

    @Override
    public List<Publicacion> listarPublicacionesPorComunidad(Long comunidadId) {
        return publicacionesPorComunidad.getOrDefault(comunidadId, new ArrayList<>());
    }
}
