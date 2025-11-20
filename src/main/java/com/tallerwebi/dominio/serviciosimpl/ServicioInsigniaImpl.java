package com.tallerwebi.dominio.serviciosimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.InsigniaPremium;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.UsuarioInsignia;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

@Service
public class ServicioInsigniaImpl implements ServicioInsignia {

    @Autowired
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    @Autowired
    private RepositorioInsignia repositorioInsignia;

    public ServicioInsigniaImpl() {
    }

    public ServicioInsigniaImpl(RepositorioUsuarioInsignia repositorioUsuarioInsignia,
            RepositorioInsignia repositorioInsignia) {
        this.repositorioUsuarioInsignia = repositorioUsuarioInsignia;
        this.repositorioInsignia = repositorioInsignia;
    }

    @Override
    @Transactional
    public boolean asignarInsignia(Usuario usuario, Insignia insignia) {
        if (Boolean.FALSE.equals(usuario.esPremium()) && insignia instanceof InsigniaPremium) {
            return false;
        }

        UsuarioInsignia usuarioInsignia = new UsuarioInsignia();
        usuarioInsignia.setUsuario(usuario);
        usuarioInsignia.setInsignia(insignia);
        usuarioInsignia.setFechaObtenida(LocalDate.now());

        return repositorioUsuarioInsignia.guardar(usuarioInsignia);
    }

    @Override
    @Transactional
    public boolean asignarInsigniaFinalSiCorresponde(Usuario usuario) {

        Long ID_INSIGNIA_FINAL = 9L;

        if (repositorioUsuarioInsignia.existe(usuario.getId(), ID_INSIGNIA_FINAL)) {
            return false;
        }

        boolean tieneTodasExcepto = repositorioUsuarioInsignia
                .usuarioTieneTodasLasInsigniasExcepto(usuario.getId(), ID_INSIGNIA_FINAL);

        if (!tieneTodasExcepto) {
            return false;
        }

        Insignia insigniaFinal = repositorioInsignia.obtenerPorId(ID_INSIGNIA_FINAL);

        return asignarInsignia(usuario, insigniaFinal);

    }

    @Override
    @Transactional
    public List<Insignia> obtenerInsigniasDeUsuario(Usuario usuario) {
        List<Insignia> insigniasDelUsuario = new ArrayList<>();
        List<UsuarioInsignia> usuarioInsignias = repositorioUsuarioInsignia.obtenerPorUsuario(usuario);
        for (UsuarioInsignia ui : usuarioInsignias) {
            if (ui.getUsuario().getId().equals(usuario.getId())) {
                insigniasDelUsuario.add(ui.getInsignia());
            }
        }
        return insigniasDelUsuario;
    }

}
