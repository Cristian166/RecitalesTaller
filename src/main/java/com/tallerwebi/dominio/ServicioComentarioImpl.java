package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("servicioComentario")
public class ServicioComentarioImpl implements ServicioComentario {

    private final List<Comentario> comentarios = new ArrayList<>();

    @Override
    public Comentario crearComentario(Usuario usuario, String texto) {
        Comentario nuevo = new Comentario(usuario, texto);
        comentarios.add(nuevo);
        return nuevo;
    }

    @Override
    public List<Comentario> listarComentarios() {
        return comentarios;
    }
}
