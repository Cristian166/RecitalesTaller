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

    void asignarInsigniaPorVisitarPerfil(Usuario visitante, Usuario visitado);

    List<String> obtenerRegiones();

    List<String> obtenerEpocas();

    Usuario obtenerUsuarioPorId(Long id);

    void actualizarPerfil(Long idUsuario, String nombre, String apellido, String telefono,
                          String email, String direccion, String pais, String provincia,
                          String imagen);

    void actualizarImagenPerfil(Long id, String nombreArchivo);
}