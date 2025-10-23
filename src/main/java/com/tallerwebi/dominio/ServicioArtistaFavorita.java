package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioBandasFavoritaImpl;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioBandaslmple;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioUsuarioImpl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioArtistaFavorita implements ServicioArtistaFavoritaInterface {

    private RepositorioBandasFavoritaImpl repositorioBandaFavorita;
    private RepositorioUsuarioImpl repositorioUsuario;
    private RepositorioBandaslmple repositorioBanda;

    // Constructor with dependencies
    public ServicioArtistaFavorita(RepositorioBandasFavoritaImpl repositorioBandaFavorita,
                                RepositorioUsuarioImpl repositorioUsuario,
                                RepositorioBandaslmple repositorioBanda) {
        this.repositorioBandaFavorita = repositorioBandaFavorita;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioBanda = repositorioBanda;
    }

    @Override
    public void agregarBandasFavoritos(Long usuarioId, Long bandaId) {
        Usuario usuario = repositorioUsuario.buscarId(usuarioId);
        Artista banda = repositorioBanda.buscarPorId(bandaId);
        
        repositorioBandaFavorita.agregarBandaFavorita(usuario, banda);
    }

    @Override
    public void eliminarBandaDeFavoritos(Long usuarioId, Long bandaId) {
        Usuario usuario = repositorioUsuario.buscarId(usuarioId);
        Artista banda = repositorioBanda.buscarPorId(bandaId);
        
        repositorioBandaFavorita.eliminarBandaFavorita(usuario, banda);
    }

    @Override
    public List<Artista> obtenerArtistasFavoritosDeUsuario(Long usuarioId) {
        Usuario usuario = repositorioUsuario.buscarId(usuarioId);
        return repositorioBandaFavorita.obtenerArtistasFavoritosDeUsuario(usuario);
    }
}