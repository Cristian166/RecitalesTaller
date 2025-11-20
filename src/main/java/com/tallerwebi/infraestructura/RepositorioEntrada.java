package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

public interface RepositorioEntrada {

    List<Entrada> obtenerEntradas();

    List<Entrada> obtenerEntradasValidadas();

    List<Entrada> obtenerEntradasPorUsuario(Usuario usuario);

    void guardarEntradaPorUsuario(Entrada entrada, Usuario usuario);

    public void eliminarPorId(Long id);

    public Entrada buscarPorId(Long id);

    
}
