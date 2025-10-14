package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioBanda;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServicioBanda implements ServicioBandaInterface{
    RepositorioBanda repositoBanda;

    public List<Banda>  obtenerTodasLasBandas() {
        return repositoBanda.obtenerTodasLasBandas();
    }
}
