package com.tallerwebi.dominio.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class OpcionEncuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    private int votos = 0;

    @ManyToOne
    @JoinColumn(name = "id_encuesta")
    private Encuesta encuesta;

    @OneToMany(mappedBy = "opcion", cascade = CascadeType.ALL)
    private List<Voto> votosUsuarios = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public List<Voto> getVotosUsuarios() {
        return votosUsuarios;
    }

    public void setVotosUsuarios(List<Voto> votosUsuarios) {
        this.votosUsuarios = votosUsuarios;
    }

}
