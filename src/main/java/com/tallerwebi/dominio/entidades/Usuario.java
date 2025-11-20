package com.tallerwebi.dominio.entidades;

import java.util.ArrayList;
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

    @Column(unique = true)
    private String email;
    private String password;
    private String pais;
    private String provincia;
    private String direccion;
    private String telefono;
    private String rol;
    private String imagen;

    @Column(name = "cantidad_publicaciones")
    private Integer cantidadPublicaciones = 0;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<UsuarioInsignia> usuarioInsignias = new HashSet<>();

    @OneToMany(mappedBy = "autorPublicacion")
    private List<Publicacion> publicaciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Entrada> entradas = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios")
    private Set<Comunidad> comunidades = new HashSet<>();

    @OneToMany(mappedBy = "usuarioCreador")
    private Set<Comunidad> comunidadesCreadasPorElUsuario;

    @OneToOne(mappedBy = "usuario")
    private PreferenciaUsuario preferenciaUsuario;
    private Boolean activo = false;

    @Column(name = "es_premium")
    private Boolean esPremium = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadPublicaciones() {
        return cantidadPublicaciones;
    }

    public void setCantidadPublicaciones(Integer cantidadPublicaciones) {
        this.cantidadPublicaciones = cantidadPublicaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean esPremium() {
        return esPremium;
    }

    public void setPremium(Boolean esPremium) {
        this.esPremium = esPremium;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
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

    public void setComunidades(Set<Comunidad> comunidades) {
        this.comunidades = comunidades;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Comunidad> getComunidadesCreadasPorElUsuario() {
        return comunidadesCreadasPorElUsuario;
    }

    public void setComunidadesCreadasPorElUsuario(Set<Comunidad> comunidadesCreadasPorElUsuario) {
        this.comunidadesCreadasPorElUsuario = comunidadesCreadasPorElUsuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public boolean isEmailvalido() {
        return email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+\\.com$");
    }

    public boolean isTelefonoValido(){
        return telefono != null && telefono.matches("^\\+?[0-9]*$");
    }

    public boolean isPasswordValido(){
        return password !=null && password.length() >= 6;
    }
    public boolean esValido(){
        return isEmailvalido() && isTelefonoValido() && isPasswordValido();
    }

}
