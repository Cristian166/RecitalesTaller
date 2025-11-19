package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true)
    private String nombre;

    @Column(length = 60)
    private String descripcion;

    private String imagen;

    @Column (length = 15)
    private String paisOrigen;

    @Column(length = 15)
    private String idioma;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comunidad_usuario", joinColumns = @JoinColumn(name = "id_comunidad_fk"), inverseJoinColumns = @JoinColumn(name = "id_usuario_fk"))
    private Set<Usuario> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.ALL)
    private List<Publicacion> publicaciones;

    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.REMOVE)
    private List<Encuesta> encuestas;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioCreador; //admin

    @Transient  // Esto evita que se persista en la base de datos
    private long cantidadMiembros;

    public Comunidad() {

    }

    public Comunidad(Long id, String nombre, String descripcion, String paisOrigen, String idioma,
            Set<Usuario> usuarios, List<Publicacion> publicaciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.paisOrigen = paisOrigen;
        this.idioma = idioma;
        this.usuarios = usuarios;
        this.publicaciones = publicaciones;
    }

    // getters y setters
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

    public String getPaisOrigen() {
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

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public long getCantidadMiembros() {
        return usuarios != null ? usuarios.size() : 0;
    }

    public void setCantidadMiembros(long cantidadMiembros) {
        this.cantidadMiembros = cantidadMiembros;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comunidad comunidad = (Comunidad) o;
        return Objects.equals(id, comunidad.id);  // Comparar por ID, o por nombre si es lo que se desea
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);  // O por nombre, dependiendo de lo que sea único en la comunidad
    }
}
