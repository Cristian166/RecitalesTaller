package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.infraestructura.RepositorioEntrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioEntradaImpl implements ServicioEntrada {

    @Autowired
    private RepositorioEntrada repositorioEntrada;

    @Override
    public void crearEntrada(Entrada entrada) {
        repositorioEntrada.guardarEntrada(entrada);
    }

    @Override
    public List<Entrada> obtenerTodasMisEntradas() {
        return repositorioEntrada.obtenerEntradas();
    }

    @Override
    public void eliminarEntrada(Long id) {
        repositorioEntrada.eliminarPorId(id);
    }


    @Override
    public void validarEntrada(Long id) {
        int random = (int) (Math.random() * 10);
        Entrada entradaEncontrada = repositorioEntrada.buscarPorId(id);

        if (entradaEncontrada != null && random >= 4) {
            entradaEncontrada.setValidada(true);
             System.out.println("Nueva entrada validada: " + entradaEncontrada.getValidada());
            repositorioEntrada.guardarEntrada(entradaEncontrada);
        }
    }

    @Override
    public Entrada buscarPorId(Long id) {
       return repositorioEntrada.buscarPorId(id);
    }

    
}
