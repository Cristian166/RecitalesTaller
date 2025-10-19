package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioBandaslmple;
import org.springframework.stereotype.Service;

import java.util.List;
import com.tallerwebi.dominio.entidades.Banda;


@Service
public class ServicioBanda implements ServicioBandaInterface{
    RepositorioBandaslmple repositoBanda;

    public List<Banda>  obtenerTodasLasBandas() {
        return repositoBanda.obtenerTodasLasBandas();
    }
}
