package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Entrada;

public interface ServicioEntrada {

    //crear entrada
    void crearEntrada(Entrada entrada);

    //obtener mis entradas
    List<Entrada> obtenerTodasMisEntradas();

    void eliminarEntrada(Long id);

    void validarEntrada(Long id);

    Entrada buscarPorId(Long id);
}
