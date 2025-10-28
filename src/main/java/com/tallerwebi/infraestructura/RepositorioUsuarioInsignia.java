package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.UsuarioInsignia;

public interface RepositorioUsuarioInsignia {

    boolean guardar(UsuarioInsignia usuarioInsignia);
    boolean existe(Long usuarioId, Long insigniaId);
    List<UsuarioInsignia> obtenerPorUsuario(Usuario usuario);
}
