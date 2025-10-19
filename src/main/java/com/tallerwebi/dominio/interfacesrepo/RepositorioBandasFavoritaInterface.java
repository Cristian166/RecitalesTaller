package com.tallerwebi.dominio.interfacesrepo;

import java.util.List;
import com.tallerwebi.dominio.entidades.Banda;
import com.tallerwebi.dominio.entidades.BandaFavorita;

public interface RepositorioBandasFavoritaInterface {
    void guardar(BandaFavorita bandaFavorita);
    List<Banda> obtenerSoloBandasFavoritas(Long usuarioId);
}
