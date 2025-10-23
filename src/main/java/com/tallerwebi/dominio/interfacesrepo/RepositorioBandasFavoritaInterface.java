package com.tallerwebi.dominio.interfacesrepo;

import java.util.List;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.BandaFavorita;

public interface RepositorioBandasFavoritaInterface {
    void guardar(BandaFavorita bandaFavorita);
    List<Artista> obtenerSoloBandasFavoritas(Long usuarioId);
}
