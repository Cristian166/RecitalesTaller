package com.tallerwebi.dominio.entidades;

import java.util.List;

public class Comunidad {

    private Long id;
    private String nombre;
    private String descripcion;
    private String paisOrigen;
    private String idioma;
    private List<Usuario> miembros;
    private List<Publicacion> publicaciones;

    public Comunidad(){

    }
    public Comunidad( Long id, String nombre, String descripcion, String paisOrigen, String idioma,List<Usuario> miembros, List<Publicacion> publicaciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.paisOrigen = paisOrigen;
        this.idioma = idioma;
        this.miembros = miembros;
        this.publicaciones = publicaciones;
    }

    //getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPaisOrigen(){
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<Usuario> getMiembros() {
        return miembros;
    }
    public void setMiembros(List<Usuario> miembros) {
        this.miembros = miembros;
    }
    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }
    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }
}
