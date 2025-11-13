package com.tallerwebi.dominio.serviciosimpl;

import java.util.List;

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



}

    
    