package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);

    Usuario buscarPorEmail(String email);

    Usuario registrar(Usuario usuario) throws UsuarioExistente;

}
