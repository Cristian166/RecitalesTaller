package com.tallerwebi.dominio.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UsuarioInsignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "insignia_id", nullable = false)
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
