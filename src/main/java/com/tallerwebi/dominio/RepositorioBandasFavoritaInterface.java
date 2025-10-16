package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioBandasFavoritaInterface {
    void guardar(BandaFavorita bandaFavorita);
    List<Banda> obtenerSoloBandasFavoritas(Long usuarioId);
}
