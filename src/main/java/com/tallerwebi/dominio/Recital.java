package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Recital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recitalId;

    private String nombreRecital;
    private String localidad;

    // mappeo
    @ManyToOne
    @JoinColumn(name = "id")
    private Usuario usuario;

    //se necesita el contructor vacio
    public Recital() {}


    // Get

    public String getNombreRecital() {
        return nombreRecital;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Long getRecitalId() { return this.recitalId; }
    // set

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setNombreRecital(String nombre) {
        this.nombreRecital = nombre;
    }

    public void guardar(Recital recital) {    }

    public void setUsuario(Usuario usuario) {  this.usuario = usuario;    }

}
