package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Voto;

public interface RepositorioVoto {

    Voto guardar(Voto voto);

    Voto obtenerVotoDeUsuario(Long usuarioId, Long encuestaId);

    void eliminar(Voto voto);

}
