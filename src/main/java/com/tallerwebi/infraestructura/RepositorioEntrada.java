package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Entrada;

public interface RepositorioEntrada {

    List<Entrada> obtenerEntradas();

    public void guardarEntrada(Entrada entrada);

    public void eliminarPorId(Long id);

    public Entrada buscarPorId(Long id);
}
