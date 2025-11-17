package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Encuesta;

public interface RepositorioEncuesta {

    Boolean guardar(Encuesta encuesta);

    Encuesta obtenerEncuestaActiva(Long comunidadId);

    Encuesta obtenerPorId(Long id);

    Boolean eliminar(Long id);

}
