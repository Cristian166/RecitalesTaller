package com.tallerwebi.infraestructura.DTOs;

import java.util.List;

public class EncuestaDTO {

    private Long id;
    private String pregunta;
    private boolean activa;

    private Long idComunidad;
    private Long idCreador;
    private String nombreCreador;

    private List<OpcionEncuestaDTO> opciones;
    private int totalVotos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Long getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Long idComunidad) {
        this.idComunidad = idComunidad;
    }

    public Long getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(Long idCreador) {
        this.idCreador = idCreador;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public List<OpcionEncuestaDTO> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionEncuestaDTO> opciones) {
        this.opciones = opciones;
    }
    public int getTotalVotos() {
        return totalVotos;
    }
    public void setTotalVotos(int totalVotos) {
        this.totalVotos = totalVotos;
    }

}
