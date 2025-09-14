package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioInsignia {

    List<Insignia> obtenerInsigniasDeUsuario(Long usuarioId);

    void asignarInsignia(Long usuarioId, Long insigniaId) throws Exception;



}
