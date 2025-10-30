package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tallerwebi.dominio.entidades.UsuarioInsignia;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioInsignia repositorioInsignia;
    private ServicioInsignia servicioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario, RepositorioInsignia repositorioInsignia,
            ServicioInsignia servicioInsignia, RepositorioUsuarioInsignia repositorioUsuarioInsignia) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioInsignia = repositorioInsignia;
        this.servicioInsignia = servicioInsignia;
        this.repositorioUsuarioInsignia = repositorioUsuarioInsignia;
    }

    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario(String email, String password) {

        Usuario usuario = repositorioUsuario.buscarUsuario(email, password);

        if (usuario != null) {
            Insignia insigniaLogin = repositorioInsignia.obtenerPorId(5L);

            if (insigniaLogin != null) {
                boolean yaTiene = repositorioUsuarioInsignia.existe(usuario.getId(), insigniaLogin.getId());

                if (!yaTiene) {
                    servicioInsignia.asignarInsignia(usuario, insigniaLogin);
                    System.out.println("Insignia asignada al usuario: " + usuario.getEmail());
                } else {
                    System.out.println("El usuario ya tiene la insignia.");
                }
            }
        }
        return usuario;
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }

}
