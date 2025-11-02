package com.tallerwebi.dominio.serviciosimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.ServicioSuscripcion;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuario;

@Service
public class ServicioSuscripcionImpl implements ServicioSuscripcion {

    @Autowired
    private ServicioInsignia servicioInsignia;

    @Autowired
    private RepositorioInsignia repositorioInsignia;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public ServicioSuscripcionImpl() {
    }

    public ServicioSuscripcionImpl(RepositorioUsuario repositorioUsuario,
            RepositorioInsignia repositorioInsignia,
            ServicioInsignia servicioInsignia) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioInsignia = repositorioInsignia;
        this.servicioInsignia = servicioInsignia;
    }

    @Override
    @Transactional
    public void procesarPagoPremium(Usuario usuario) {

        if (usuario == null) {
            return;
        }

        Usuario usuarioPremium = repositorioUsuario.buscarId(usuario.getId());

        if (usuarioPremium == null) {
            return;
        }

        usuarioPremium.setEsPremium(true);
        repositorioUsuario.modificar(usuarioPremium);

        Insignia insigniaPremium = repositorioInsignia.obtenerPorId(8L);

        if (insigniaPremium != null) {
            servicioInsignia.asignarInsignia(usuarioPremium, insigniaPremium);
            System.out.println("Insignia de premium asignada");
        }

    }

}
