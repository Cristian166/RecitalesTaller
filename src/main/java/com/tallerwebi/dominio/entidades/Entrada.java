package com.tallerwebi.dominio.entidades;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Entrada {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;

    private String nombreRecital;
    private String lugar;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horario;

    private String seccion;
    private Boolean validada=false;

    

    public Entrada(){
    }

    public Entrada(Long id, String nombreRecital,String lugar, LocalDate fecha, LocalTime horario,String seccion){
        this.id = id;
        this.nombreRecital = nombreRecital;
        this.lugar = lugar;
        this.fecha = fecha;
        this.horario = horario;
        this.seccion = seccion;
    }

    public Long getId() {
        return this.id;
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
    public LocalTime getHorario() {
        return horario;
    }
    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
    public String getSeccion() {
        return seccion;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    public Boolean getValidada() {
        return validada;
    }

    public void setValidada(Boolean validada) {
        this.validada = validada;
    }

}
