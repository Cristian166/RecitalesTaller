package com.tallerwebi.dominio;

import java.time.LocalDate;

public class UsuarioInsignia {

    private long id;
    private Usuario usuario;
    private Insignia insignia;
    private LocalDate fechaObtenida;

    public long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Insignia getInsignia() {
        return insignia;
    }

    public LocalDate getFechaObtenida() {
        return fechaObtenida;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setInsignia(Insignia insignia) {
        this.insignia = insignia;
    }

    public void setFechaObtenida(LocalDate fechaObtenida) {
        this.fechaObtenida = fechaObtenida;
    }

}
