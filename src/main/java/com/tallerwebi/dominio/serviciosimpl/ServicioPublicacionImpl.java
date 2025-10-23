package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioPublicacionImpl implements ServicioPublicacion {

    private Map<Long, List<Publicacion>> publicacionesPorComunidad = new HashMap<>();

    @Override
    public void crearPublicacion(Publicacion publicacion, Long comunidadId, MultipartFile imagen) {
        publicacionesPorComunidad.putIfAbsent(comunidadId, new ArrayList<>());

        if(imagen != null && !imagen.isEmpty()) {
            publicacion.setImagenUrl("/uploads/"+ imagen.getOriginalFilename());
        }

        publicacionesPorComunidad.get(comunidadId).add(publicacion);
    }

    @Override
    public List<Publicacion> listarPublicacionesPorComunidad(Long comunidadId) {
        return publicacionesPorComunidad.getOrDefault(comunidadId, new ArrayList<>());
    }
}
