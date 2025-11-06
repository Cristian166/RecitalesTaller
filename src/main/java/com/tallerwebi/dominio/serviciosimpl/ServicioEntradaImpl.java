package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioEntrada;
import com.tallerwebi.infraestructura.DTOs.EntradaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioEntradaImpl implements ServicioEntrada {

    @Autowired
    private RepositorioEntrada repositorioEntrada;

    public ServicioEntradaImpl(RepositorioEntrada repositorioEntrada){
        this.repositorioEntrada = repositorioEntrada;
    }

    @Override
    public void crearEntrada(Entrada entrada, Usuario usuario) {
        repositorioEntrada.guardarEntradaPorUsuario(entrada, usuario);
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
    public void validarEntrada(Long id, Usuario usuario) {
        validarEntrada(id, usuario, (int) (Math.random() * 10));
    }



    public void validarEntrada(Long id, Usuario usuario, int random) {
        Entrada entradaEncontrada = repositorioEntrada.buscarPorId(id);

        if (entradaEncontrada != null && random >= 4) {
            entradaEncontrada.setValidada(true);
            repositorioEntrada.guardarEntradaPorUsuario(entradaEncontrada, usuario);
        }
    }

    @Override
    public Entrada buscarPorId(Long id) {
       return repositorioEntrada.buscarPorId(id);
    }

    @Override
    public List<EntradaDTO> obtenerEntradasPorUsuario(Usuario usuario) {
       List<Entrada> entradas = repositorioEntrada.obtenerEntradasPorUsuario(usuario);
        return entradas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private EntradaDTO convertirADTO(Entrada entrada) {
    return new EntradaDTO(
        entrada.getId(),
        entrada.getNombreRecital(),
        entrada.getLugar(),
        entrada.getFecha(),
        entrada.getHorario(),
        entrada.getSeccion(),
        entrada.getImagen()
    );
}

    
}
