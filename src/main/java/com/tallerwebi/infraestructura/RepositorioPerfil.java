package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Preferencia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;

public interface RepositorioPerfil {

    List<Preferencia> obtenerPreferenciaExistentes();

    List<String> obtenerNombresDePreferenciasPorTipo(String tipo);

    PreferenciaUsuario obtenerPreferenciasPorUsuario(Usuario usuario);

    void guardarPreferenciasPorUsuario(Long idUsuario, List<String> generosSeleccionados,
            List<String> artistasSeleccionados, List<String> regionesSeleccionadas, List<String> epocasSeleccionadas);

    Usuario obtenerUsuarioPorId(Long id);
}
