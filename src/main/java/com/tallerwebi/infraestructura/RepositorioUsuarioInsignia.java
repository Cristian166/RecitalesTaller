package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.UsuarioInsignia;

public interface RepositorioUsuarioInsignia {

    void guardar(UsuarioInsignia usuarioInsignia);

    void modificar(UsuarioInsignia usuarioInsignia);

    UsuarioInsignia buscar(Long id);

    List<UsuarioInsignia> listarPorUsuario(Long usuarioId);

}
