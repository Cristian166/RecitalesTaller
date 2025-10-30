package com.tallerwebi.dominio.entidades;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<UsuarioInsignia> usuarioInsignias = new HashSet<>();

    @OneToMany(mappedBy = "autorPublicacion")
    private List<Publicacion> publicaciones;

    @ManyToMany(mappedBy = "usuarios")
    private Set<Comunidad> comunidades = new HashSet<>();

    @OneToOne(mappedBy = "usuario")
    private PreferenciaUsuario preferenciaUsuario;
    private Boolean activo = false;
    private Boolean esPremium = false;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    
    public Boolean esPremium() { return esPremium;}
    public void setPremium(Boolean esPremium) {this.esPremium = esPremium;}

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password;}

    public String getRol() { return rol;}
    public void setRol(String rol) {this.rol = rol;}

    public Boolean getActivo() { return activo;}
    public void setActivo(Boolean activo) {this.activo = activo;}

    public boolean activo() {return activo;}
    public void activar() {activo = true;
    }

    public Boolean getEsPremium() {
        return esPremium;
    }

    public void setEsPremium(Boolean esPremium) {
        this.esPremium = esPremium;
    }


    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }
    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }
    public Set<Comunidad> getComunidades() {
        return comunidades;
    }
    public  void setComunidades(Set<Comunidad> comunidades) {
        this.comunidades = comunidades;
    }

}
