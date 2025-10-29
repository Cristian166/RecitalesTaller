package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaCreacion;

    @Lob
    private String imagen;

    @Column ( length = 1000)
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk")
    private Usuario autorPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_comunidad_fk")
    private Comunidad comunidad;

    public Publicacion() {

    }
    public Publicacion( Long id, String contenido,Usuario autorPublicacion, LocalDateTime fechaCreacion, Comunidad comunidad) {
        this.id = id;
        this.contenido = contenido;
        this.autorPublicacion = autorPublicacion;
        this.fechaCreacion = fechaCreacion;
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

    public String getImagen(){
        return imagen;
    }
    public void setImagen(String imagen){
        this.imagen = imagen;
    }
    public Comunidad getComunidad(){
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public String getEmailAutor(){
        return autorPublicacion.getEmail();
    }
}
