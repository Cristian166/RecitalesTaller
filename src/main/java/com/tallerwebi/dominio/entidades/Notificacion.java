package com.tallerwebi.dominio.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Notificacion {

    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombreNotificacion;
    private String descripcionNotificacion;
    private String link;

    private Boolean vista=false;

    public Notificacion(){

    }

    public Notificacion(Long id, Usuario usuario, String nombreNotificacion, String descripcionNotificacion,
            Boolean vista) {
        this.id = id;
        this.usuario = usuario;
        this.nombreNotificacion = nombreNotificacion;
        this.descripcionNotificacion = descripcionNotificacion;
        this.vista = vista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreNotificacion() {
        return nombreNotificacion;
    }

    public void setNombreNotificacion(String nombreNotificacion) {
        this.nombreNotificacion = nombreNotificacion;
    }

    public String getDescripcionNotificacion() {
        return descripcionNotificacion;
    }

    public void setDescripcionNotificacion(String descripcionNotificacion) {
        this.descripcionNotificacion = descripcionNotificacion;
    }

    public Boolean getVista() {
        return vista;
    }

    public void setVista(Boolean vista) {
        this.vista = vista;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getLink() {
        return link;
    }
    

}
