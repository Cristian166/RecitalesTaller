package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Artista;
import java.util.List;

public interface ServicioArtistaFavoritaInterface {
    void agregarBandasFavoritos(Long usuarioId, Long bandaId);
    void eliminarBandaDeFavoritos(Long usuarioId, Long bandaId);
    List<Artista> obtenerArtistasFavoritosDeUsuario(Long usuarioId);  // ← Return type is List<Artista>
}