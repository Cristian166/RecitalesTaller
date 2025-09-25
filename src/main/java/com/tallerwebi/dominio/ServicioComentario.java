package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioComentario {

    Comentario crearComentario(Usuario usuario, String texto);
    List<Comentario> listarComentarios();
}
