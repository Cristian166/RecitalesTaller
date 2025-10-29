package com.tallerwebi.dominio.excepcion;

public class UsuarioExistente extends Exception {

    public UsuarioExistente(String mensaje) {
        super(mensaje);
    }

    public UsuarioExistente() {
        super("El usuario con ese email ya existe.");
    }
}
