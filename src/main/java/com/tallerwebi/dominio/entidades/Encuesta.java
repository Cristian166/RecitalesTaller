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
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pregunta;

    private boolean activa = true;

    @ManyToOne
    @JoinColumn(name = "id_comunidad")
    private Comunidad comunidad;

    @ManyToOne
    @JoinColumn(name = "id_creador")
    private Usuario creador;

    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL)
    private List<OpcionEncuesta> opciones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalVotos() {
        return opciones.stream().mapToInt(OpcionEncuesta::getVotos).sum();
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public List<OpcionEncuesta> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionEncuesta> opciones) {
        this.opciones = opciones;
    }

}
