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
    public Usuario registrar(Usuario usuario) throws UsuarioExistente {
        validarCampos(usuario);
        Usuario usuarioEncontrado = repositorioUsuario.buscarPorEmail(usuario.getEmail());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente("El email ya está registrado.");
        }
         /*
    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente();
            */
        repositorioUsuario.guardar(usuario);
        return usuario;
    }

    private void validarCampos(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("Debe ingresar un email válido.");
        }
        if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return repositorioUsuario.buscarPorEmail(email);
    }

}
