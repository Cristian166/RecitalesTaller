/*
DESPUES LO ARREGLO XD

package com.tallerwebi.infraestructura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.Insignia;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioInsignia;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service("repositorioInsignia")
public class RepositorioInsigniaBaseImpl implements ServicioInsignia {

    private RepositorioInsigniaBase repositorioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public RepositorioInsigniaBaseImpl(RepositorioInsigniaBase repositorioInsignia,
                                RepositorioUsuarioInsignia repositorioUsuarioInsignia,
                                RepositorioUsuario repositorioUsuario) {
        this.repositorioInsignia = repositorioInsignia;
        this.repositorioUsuarioInsignia = repositorioUsuarioInsignia;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Insignia> obtenerInsigniasDeUsuario(Long usuarioId) {
        List<UsuarioInsignia> usuarioInsignias = repositorioUsuarioInsignia.listarPorUsuario(usuarioId);
        return usuarioInsignias.stream()
                .map(UsuarioInsignia::getInsignia)
                .collect(Collectors.toList());
    }

    @Override
    public void asignarInsignia(Long usuarioId, Long insigniaId) throws Exception {
        Usuario usuario = repositorioUsuario.buscar(usuarioId.toString());
        Insignia insignia = repositorioInsignia.buscar(insigniaId);

        if (usuario == null || insignia == null) {
            throw new Exception("Usuario o insignia no encontrado");
        }

        List<UsuarioInsignia> existentes = repositorioUsuarioInsignia.listarPorUsuario(usuario.getId());
        boolean yaTiene = false;
        for (UsuarioInsignia ui : existentes) {
            if (ui.getInsignia() != null && ui.getInsignia().getId() == insigniaId) {
                yaTiene = true;
                break;
            }
        }

        if (!yaTiene) {
            UsuarioInsignia usuarioInsignia = new UsuarioInsignia();
            usuarioInsignia.setUsuario(usuario);
            usuarioInsignia.setInsignia(insignia);
            usuarioInsignia.setFechaObtenida(LocalDate.now());
            repositorioUsuarioInsignia.guardar(usuarioInsignia);
        }
    }

}
*/