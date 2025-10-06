package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ServicioInsigniaImpl implements ServicioInsignia {

    private List<UsuarioInsignia> usuarioInsignias = new ArrayList<>();

    @Override
    public boolean asignarInsignia(Usuario usuario, Insignia insignia) {
        UsuarioInsignia usuarioInsignia = new UsuarioInsignia();
        usuarioInsignia.setId(usuarioInsignias.size() + 1);
        usuarioInsignia.setUsuario(usuario);
        usuarioInsignia.setInsignia(insignia);
        usuarioInsignia.setFechaObtenida(LocalDate.now());

        return usuarioInsignias.add(usuarioInsignia);
    }

    @Override
    public List<Insignia> obtenerInsigniasDeUsuario(Usuario usuario) {
    List<Insignia> insigniasDelUsuario = new ArrayList<>();
    for (UsuarioInsignia ui : usuarioInsignias) {
        if (ui.getUsuario().getId().equals(usuario.getId())) {
            insigniasDelUsuario.add(ui.getInsignia());
        }
    }
    return insigniasDelUsuario;
    }
}
