package com.tallerwebi.dominio.entidades;

import java.time.LocalDateTime;

public class Publicacion {

    private Long id;
    private String contenido;
    private Usuario autorPublicacion;
    private LocalDateTime fechaCreacion;
    private String fechaCreacionString;
    private String imagen;

    public Publicacion() {

    }

    public Publicacion( Long id, String contenido,Usuario autorPublicacion, LocalDateTime fechaCreacion) {
        this.id = id;
        this.contenido = contenido;
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
    public String getFechaCreacionString(){
        return fechaCreacionString;
    }
    public void setFechaCreacionString(String fechaCreacionString){
        this.fechaCreacionString = fechaCreacionString;
    }
    public String getImagen(){
        return imagen;
    }
    public void setImagen(String imagen){
        this.imagen = imagen;
    }
}
