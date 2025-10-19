package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Preferencia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;

public interface ServicioPerfil {

    List<Preferencia> consultarPreferenciaExistentes();
    

    void guardarPreferencias(Long idUsuario, List<String> generosSeleccionados,
                             List<String> artistasSeleccionados,
                             List<String> regionesSeleccionadas,
                             List<String> epocasSeleccionadas);

    PreferenciaUsuario obtenerPreferenciasPorUsuario(Usuario usuario);


    List<String> obtenerGeneros();


    List<String> obtenerArtistas();


    List<String> obtenerRegiones();


    List<String> obtenerEpocas();

}