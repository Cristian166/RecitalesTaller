package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.dominio.entidades.Usuario;

public interface ServicioEncuesta {

    Encuesta obtenerEncuestaActiva(Long comunidadId);

    Encuesta crearEncuesta(Comunidad comunidad, Usuario creador, String pregunta, List<String> opciones);

    Boolean votar(Long opcionId, Usuario usuario);

    Boolean eliminarEncuesta(Long encuestaId);

}
