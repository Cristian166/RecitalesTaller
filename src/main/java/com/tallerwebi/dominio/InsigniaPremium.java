package com.tallerwebi.dominio;

public class InsigniaPremium extends Insignia {

    private String efectoVisual;

    public InsigniaPremium(String efectoVisual) {
        super();
        this.efectoVisual = efectoVisual;
    }

    public String getEfectoVisual() {
        return efectoVisual;
    }
    public void setEfectoVisual(String efectoVisual) {
        this.efectoVisual = efectoVisual;
    }


}
