package com.tallerwebi.dominio.serviciosimpl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.ServicioCalendario;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioCalendario;

@Service
@Transactional
public class ServicioCalendarioImpl implements ServicioCalendario {

    @Autowired
    private RepositorioCalendario repositorioCalendario;

    public ServicioCalendarioImpl(RepositorioCalendario repositorioCalendario){
        this.repositorioCalendario = repositorioCalendario;
    }

    @Override
    public List<Entrada> obtenerEventos(Usuario usuario) {
        return repositorioCalendario.obtenerEventos(usuario);
    }

    @Override
    public List<Entrada> obtenerEntradasFuturas(Usuario usuario) {
        LocalDate hoy = LocalDate.now();

        return obtenerEventos(usuario).stream()
                .filter(e -> e.getFecha() != null &&
                             (e.getFecha().isAfter(hoy) || e.getFecha().isEqual(hoy)))
                .sorted(Comparator.comparing(Entrada::getFecha))
                .collect(Collectors.toList());
    }

    @Override
    public List<Entrada> obtenerEntradasPasadas(Usuario usuario) {
        LocalDate hoy = LocalDate.now();

        return obtenerEventos(usuario).stream()
                .filter(e -> e.getFecha() != null &&
                             e.getFecha().isBefore(hoy))
                .sorted(Comparator.comparing(Entrada::getFecha).reversed())
                .collect(Collectors.toList());
    }


}

    
    