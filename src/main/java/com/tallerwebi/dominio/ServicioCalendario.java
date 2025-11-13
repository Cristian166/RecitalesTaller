package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;

public interface ServicioCalendario {

     List<Entrada> obtenerEventos(Usuario usuario);

    
}
