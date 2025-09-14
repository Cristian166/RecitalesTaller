package com.tallerwebi.dominio;
import java.util.List;


public interface RepositorioInsignia {

    void guardar(UsuarioInsignia usuarioInsignia);

    void modificar(UsuarioInsignia usuarioInsignia);

    UsuarioInsignia buscar(Long id);

    List<UsuarioInsignia> listarPorUsuario(Long usuarioId);

    
}
