package com.tallerwebi.dominio.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Banda {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long banda_id;

    private String nombreBanda;

    public Banda() {}

    public void setNombre(String nombreBanda) {
        this.nombreBanda = nombreBanda;
    }

}
