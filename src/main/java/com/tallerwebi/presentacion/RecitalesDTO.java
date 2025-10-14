package com.tallerwebi.presentacion;

public class RecitalesDTO {

    private long usuarioId;
    private String nombre;
    private String apellido;

    public RecitalesDTO(long usuarioId, String nombre, String apellido) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }


}
