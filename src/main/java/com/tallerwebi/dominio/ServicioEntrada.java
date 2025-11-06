package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.EntradaDTO;

public interface ServicioEntrada {

    void crearEntrada(Entrada entrada, Usuario usuario);

    List<Entrada> obtenerTodasMisEntradas();
    List<EntradaDTO> obtenerEntradasPorUsuario(Usuario usuario);

    void eliminarEntrada(Long id);

    void validarEntrada(Long id, Usuario usuario);

    Entrada buscarPorId(Long id);
}
