package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEntrada {

    //crear entrada
    void crearEntrada(Entrada entrada);

    //obtener mis entradas
    List<Entrada> obtenerTodasMisEntradas();

    void eliminarEntrada(Long id);
}
