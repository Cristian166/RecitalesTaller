package com.tallerwebi.dominio;

public class Comentario {
    private Long id;
    private Usuario usuario;
    private String texto;

    public Comentario() {
        this.usuario = new Usuario();
    }

    public Comentario(Usuario usuario, String texto) {
        this.usuario = usuario;
        this.texto = texto;
    }

    public Comentario(Long id, Usuario usuario, String texto) {
        this.id = id;
        this.usuario = usuario;
        this.texto = texto;
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

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
