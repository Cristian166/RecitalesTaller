package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.PublicacionDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioPublicacion {
    Boolean crearPublicacion(Publicacion publicacion, Long comunidadId, Usuario usuario);
    List<Publicacion> listarPublicacionesPorComunidad(Long comunidadId);
    List<PublicacionDTO> listarPublicacionesDTOPorComunidad(Long id);
}