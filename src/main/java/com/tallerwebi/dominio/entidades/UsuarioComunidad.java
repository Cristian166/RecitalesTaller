package com.tallerwebi.dominio.entidades;

import javax.persistence.*;

@Entity
public class UsuarioComunidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_comunidad_fk")
    private Comunidad comunidad;

    private String rol; //creador o miembro

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
    public Comunidad getComunidad() {
        return comunidad;
    }
    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
