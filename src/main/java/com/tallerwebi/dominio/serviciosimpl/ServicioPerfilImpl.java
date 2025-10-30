package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.entidades.Preferencia;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioPerfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service("servicioPerfil")
@Transactional
public class ServicioPerfilImpl implements ServicioPerfil {

    private RepositorioPerfil repositorioPerfil;

     @Autowired
    public ServicioPerfilImpl(RepositorioPerfil repositorioPerfil) {
        this.repositorioPerfil = repositorioPerfil;
    }

    @Override
    public List<Preferencia> consultarPreferenciaExistentes() {
        return repositorioPerfil.obtenerPreferenciaExistentes();
    }

    @Override
    public List<String> obtenerGeneros() {
        return repositorioPerfil.obtenerNombresDePreferenciasPorTipo("genero");
    }


    @Override
    public List<String> obtenerArtistas() {
        return repositorioPerfil.obtenerNombresDePreferenciasPorTipo("artista");
    }

    @Override
    public List<String> obtenerRegiones() {
        return repositorioPerfil.obtenerNombresDePreferenciasPorTipo("region");
    }

    @Override
    public List<String> obtenerEpocas() {
        return repositorioPerfil.obtenerNombresDePreferenciasPorTipo("epoca");
    }

    @Override
   public PreferenciaUsuario obtenerPreferenciasPorUsuario(Usuario usuario) {
        PreferenciaUsuario preferencias = repositorioPerfil.obtenerPreferenciasPorUsuario(usuario);

        if (preferencias != null) {
            preferencias.getGenerosSeleccionados().size();
            preferencias.getArtistasSeleccionados().size();
            preferencias.getRegionesSeleccionadas().size();
            preferencias.getEpocasSeleccionadas().size();
        }

        return preferencias;
    }

    @Override
    public void guardarPreferencias(Long idUsuario, List<String> generosSeleccionados, List<String> artistasSeleccionados, List<String> regionesSeleccionadas, List<String> epocasSeleccionadas) {
        repositorioPerfil.guardarPreferenciasPorUsuario(idUsuario, generosSeleccionados, artistasSeleccionados, regionesSeleccionadas, epocasSeleccionadas);
    }

    


}
