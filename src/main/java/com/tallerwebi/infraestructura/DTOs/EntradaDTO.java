package com.tallerwebi.infraestructura.DTOs;

import java.time.LocalDate;

public class EntradaDTO {

    private Long id;
    private String nombreRecital;
    private String lugar;
    private LocalDate fecha;
    private String horario;
    private String imagen;
    private String seccion;
    private Boolean validada;
    public EntradaDTO(){
    }

    public EntradaDTO(Long id, String nombreRecital,String lugar, LocalDate fecha, String horario,String seccion, String imagen, Boolean validada) {
        this.id = id;
        this.nombreRecital = nombreRecital;
        this.lugar = lugar;
        this.fecha = fecha;
        this.horario = horario;
        this.seccion = seccion;
        this.imagen=imagen;
        this.validada=validada;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreRecital() {
        return nombreRecital;
    }
    public void setNombreRecital(String nombreRecital) {
        this.nombreRecital = nombreRecital;
    }
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String getSeccion() {
        return seccion;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

   public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getValidada() {
        return validada;
    }

    public void setValidada(Boolean validada) {
        this.validada = validada;
    }
}
