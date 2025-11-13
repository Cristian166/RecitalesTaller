package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

public interface RepositorioCalendario {

    List<Entrada> obtenerEventos(Usuario usuario);

}
