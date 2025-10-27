package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.serviciosimpl.ServicioArtistaFavoritoImpl;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioBandaslmple;


@Service
public class ServicioArtista implements ServicioArtistaFavoritoImpl{
    RepositorioBandaslmple repositoBanda;

    public List<Artista>  obtenerTodasLasBandas() {
        return repositoBanda.obtenerTodasLasBandas();
    }
}
