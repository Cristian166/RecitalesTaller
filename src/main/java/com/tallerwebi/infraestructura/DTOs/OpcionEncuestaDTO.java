package com.tallerwebi.infraestructura.DTOs;

import java.util.List;

public class OpcionEncuestaDTO {

    private String pregunta;
    private Long idComunidad;
    private List<String> opciones;

    public String getPregunta() {
        return pregunta;
    }

    public Long getIdComunidad() {
        return idComunidad;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setIdComunidad(Long idComunidad) {
        this.idComunidad = idComunidad;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

}
