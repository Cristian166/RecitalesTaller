package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.Preferencia;
import com.tallerwebi.dominio.PreferenciaUsuario;
import com.tallerwebi.dominio.Usuario;

public interface RepositorioPerfil {

    List<Preferencia> obtenerPreferenciaExistentes();

    List<String> obtenerNombresDePreferenciasPorTipo(String tipo);

    PreferenciaUsuario obtenerPreferenciasPorUsuario(Usuario usuario);

    void guardarPreferenciasPorUsuario(Long idUsuario, List<String> generosSeleccionados,
            List<String> artistasSeleccionados, List<String> regionesSeleccionadas, List<String> epocasSeleccionadas);

}
