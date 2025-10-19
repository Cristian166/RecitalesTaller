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
        List<String> generos = repositorioPerfil.obtenerNombresDePreferenciasPorTipo("genero");
         generos = List.of("Rock", "Pop", "Rap", "Reggaeton", "Jazz");
        return generos;
    }


    @Override
    public List<String> obtenerArtistas() {
        List<String> artistas = repositorioPerfil.obtenerNombresDePreferenciasPorTipo("artista");
        artistas = List.of("Duki", "Emilia", "Bad bunny", "Gustavo Ceratti", "Lali");
        return artistas;
    }

    @Override
    public List<String> obtenerRegiones() {
        List<String> regiones = repositorioPerfil.obtenerNombresDePreferenciasPorTipo("region");
         regiones = List.of("Europa", "España", "Argentina", "Mexico", "EEUU");
        return regiones;
    }

    @Override
    public List<String> obtenerEpocas() {
        List<String> epocas = repositorioPerfil.obtenerNombresDePreferenciasPorTipo("epoca");
         epocas = List.of("Clasica", "80's", "2000's", "Actualidad", "90's");
        return epocas;
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
