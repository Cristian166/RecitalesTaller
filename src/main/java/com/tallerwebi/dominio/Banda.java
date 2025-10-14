package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Banda {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nombreBanda;

    public void setNombre(String nombreBanda) {
        this.nombreBanda = nombreBanda;
    }

}
