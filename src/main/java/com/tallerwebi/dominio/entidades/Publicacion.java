package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = 500)
    private String contenido;

    private LocalDateTime fechaCreacion;

    private String imagen;

    @Transient
    private String fechaFormateada;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk")
    private Usuario autorPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_comunidad_fk")
    private Comunidad comunidad;

    public Publicacion() {

    }
    public Publicacion(String contenido, Usuario autorPublicacion, Comunidad comunidad) {
        this.contenido = contenido;
        this.autorPublicacion = autorPublicacion;
        this.comunidad = comunidad;
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
    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }
    public String getImagen(){
        return imagen;
    }
    public void setImagen(String imagen){
        this.imagen = imagen;
    }
    public String getFechaFormateada(){
        return fechaFormateada;
    }
    public void setFechaFormateada(String fechaFormateada){
        this.fechaFormateada = fechaFormateada;
    }
    public Usuario getAutorPublicacion(){
        return autorPublicacion;
    }
    public void setAutorPublicacion(Usuario usuario) {
        this.autorPublicacion = usuario;
    }
    public Comunidad getComunidad(){
        return comunidad;
    }
    public void setComunidad(Comunidad comunidad){
        this.comunidad = comunidad;
    }
}
