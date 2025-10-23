package com.tallerwebi.dominio.entidades;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PreferenciaUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario usuario;

    
    @ElementCollection
    @CollectionTable(name = "preferenciaUsuarioGeneros", joinColumns = @JoinColumn(name = "preferenciaUsuarioId"))
    @Column(name = "genero")
    private List<String> generosSeleccionados;

    @ElementCollection
    @CollectionTable(name = "preferenciaUsuarioArtistas", joinColumns = @JoinColumn(name = "preferenciaUsuarioId"))
    @Column(name = "artista")
    private List<String> artistasSeleccionados;

    @ElementCollection
    @CollectionTable(name = "preferenciaUsuarioRegiones", joinColumns = @JoinColumn(name = "preferenciaUsuarioId"))
    @Column(name = "region")
    private List<String> regionesSeleccionadas;

    @ElementCollection
    @CollectionTable(name = "preferenciaUsuarioEpocas", joinColumns = @JoinColumn(name = "preferenciaUsuarioId"))
    @Column(name = "epoca")
    private List<String> epocasSeleccionadas;
    
     public PreferenciaUsuario() {}
    
    public PreferenciaUsuario(Usuario usuario, List<String> generosSeleccionados, List<String> artistasSeleccionados,
            List<String> regionesSeleccionadas, List<String> epocasSeleccionadas) {
        this.usuario = usuario;
        this.generosSeleccionados = generosSeleccionados;
        this.artistasSeleccionados = artistasSeleccionados;
        this.regionesSeleccionadas = regionesSeleccionadas;
        this.epocasSeleccionadas = epocasSeleccionadas;
    }


    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}
    public List<String> getGenerosSeleccionados() { return generosSeleccionados; }
    public void setGenerosSeleccionados(List<String> generosSeleccionados) {this.generosSeleccionados = generosSeleccionados;}
    public List<String> getArtistasSeleccionados() {return artistasSeleccionados;}
    public void setArtistasSeleccionados(List<String> artistasSeleccionados) { this.artistasSeleccionados = artistasSeleccionados; }
    public List<String> getRegionesSeleccionadas() { return regionesSeleccionadas;}
    public void setRegionesSeleccionadas(List<String> regionesSeleccionadas) {this.regionesSeleccionadas = regionesSeleccionadas;}
    public List<String> getEpocasSeleccionadas() { return epocasSeleccionadas; }
    public void setEpocasSeleccionadas(List<String> epocasSeleccionadas) { this.epocasSeleccionadas = epocasSeleccionadas;}
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}


}
