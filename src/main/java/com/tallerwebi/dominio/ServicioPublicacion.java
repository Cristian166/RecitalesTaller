package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioPublicacion {
    void crearPublicacion(Publicacion publicacion, Long comunidadId, MultipartFile imagen);
    List<Publicacion> listarPublicacionesPorComunidad(Long comunidadId);
}