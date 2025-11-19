package com.tallerwebi.infraestructura.DTOs;

public class PublicacionDTO {
    private String contenido;
    private String fechaFormateada;
    private String autorNombre;
    private String imagen;
    private Long id;
    private Boolean destacada;

    public PublicacionDTO() {

    }

    public PublicacionDTO(String contenido, String fechaFormateada,
            String autorNombre, String imagen, Long id) {
        this.contenido = contenido;
        this.fechaFormateada = fechaFormateada;
        this.autorNombre = autorNombre;
        this.imagen = imagen;
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDestacada() {
        return destacada;
    }

    public void setDestacada(Boolean destacada) {
        this.destacada = destacada;
    }
}
