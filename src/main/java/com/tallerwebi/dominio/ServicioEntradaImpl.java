package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Entrada;
import com.tallerwebi.dominio.ServicioEntrada;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioEntradaImpl implements ServicioEntrada {

    private List<Entrada> entradas = new ArrayList<>();

    @Override
    public void crearEntrada(Entrada entrada) {
        Long nuevoID = System.currentTimeMillis();
        entrada.setId(nuevoID);
        entradas.add(entrada);
    }

    @Override
    public List<Entrada> obtenerTodasMisEntradas() {
        return entradas;
    }

    @Override
    public void eliminarEntrada(Long id) {
        entradas.removeIf(entrada -> entrada.getId().equals(id));
    }
}

