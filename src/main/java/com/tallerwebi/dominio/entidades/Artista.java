package com.tallerwebi.dominio.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Artista {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String genero;

    public Artista() {}

    public void setNombre(String nombreArtista) {
        this.nombre = nombreArtista;
    }

    public void setId(long l) {
        this.id = l;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Object getNombre() {
        return this.nombre;
    }

}
