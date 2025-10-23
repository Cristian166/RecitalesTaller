package com.tallerwebi.dominio.entidades;

import javax.persistence.*;

@Entity
public class BandaFavorita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bandaFavorita_id;

    //mapeo usuario
    @ManyToOne
    @JoinColumn(name = "id")
    private Usuario usuario;

    //mappeo banda
    @ManyToOne
    @JoinColumn(name = "banda_id")
    private Artista banda;

    public BandaFavorita() {}

    public void setBanda(Artista banda) { this.banda = banda; }

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public Artista getBanda() {
        return banda;
    }
    public Usuario getUsuario() {
        return usuario;
    }


    public Long getId() {
        return bandaFavorita_id;
    }
    }

