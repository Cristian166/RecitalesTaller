package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;
@Service("servicioResitalVisitado")
public class ServicioVisitasImp implements  ServicioVisitasInterfaz {

    @Override
    public List<Recital> obtenerRecitales() {
        return List.of(
                new Recital("Recital 1", -34.6037, -58.3816),
                new Recital("Recital 2", -34.6158, -58.4333),
                new Recital("Recital 3", -34.6083, -58.3712)
        );
    }
}
