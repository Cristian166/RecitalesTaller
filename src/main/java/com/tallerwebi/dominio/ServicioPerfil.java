package com.tallerwebi.dominio;

import java.util.List;

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