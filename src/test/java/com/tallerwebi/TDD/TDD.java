package com.tallerwebi.TDD;

public class TDD {

    public static String validarFortalezaContrasena(String contrasena){
        String fortaleza = "";
        if(contrasena.length()<8){
            fortaleza = "INVALIDA";
        }
        else if(contrasena.length()>=8 && contrasena.contains("@") && contrasena.contains("1234")){
            fortaleza = "FUERTE";
        }
        else if (contrasena.length()>=8 && contrasena.contains("@")) {
            fortaleza = "MEDIANA";
        }
        else if(contrasena.length()>=8){
            fortaleza = "DEBIL";
        }
        return fortaleza;
    }

    public static String clasificarTemperatura(Integer temperatura) {

        String calificacion = "";
        if(temperatura <= 0){
            calificacion = "CONGELANTE";
        }
        return calificacion;
    }
}
