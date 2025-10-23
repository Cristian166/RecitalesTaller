package com.tallerwebi.dominio.entidades;

import java.time.LocalDateTime;

public class Publicacion {

    private Long id;
    private String contenido;
    private String imagenUrl;
    private Usuario autorPublicacion;
    private LocalDateTime fechaCreacion;

    public Publicacion() {

    }

    public Publicacion( Long id, String contenido, String imagenUrl, Usuario autorPublicacion, LocalDateTime fechaCreacion) {
        this.id = id;
        this.contenido = contenido;
        this.imagenUrl = imagenUrl;
        this.autorPublicacion = autorPublicacion;
        this.fechaCreacion = fechaCreacion;
    }
    //getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContenido(){
        return contenido;
    }
    public void setContenido(String contenido){
        this.contenido = contenido;
    }
    public String getImagenUrl(){
        return imagenUrl;
    }
    public void setImagenUrl(String imagenUrl){
        this.imagenUrl = imagenUrl;
    }
    public Usuario getAutorPublicacion(){
        return autorPublicacion;
    }
    public void setAutorPublicacion(Usuario autorPublicacion){
        this.autorPublicacion = autorPublicacion;
    }
    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }
}
