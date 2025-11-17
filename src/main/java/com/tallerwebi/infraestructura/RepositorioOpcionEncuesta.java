package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.OpcionEncuesta;

public interface RepositorioOpcionEncuesta {

    OpcionEncuesta guardar(OpcionEncuesta opcion);

    OpcionEncuesta obtenerPorId(Long id);

}
