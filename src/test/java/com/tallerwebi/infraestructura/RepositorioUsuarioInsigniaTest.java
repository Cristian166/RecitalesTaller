package com.tallerwebi.infraestructura;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.InsigniaPremium;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.UsuarioInsignia;
import com.tallerwebi.dominio.serviciosimpl.ServicioInsigniaImpl;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioInsigniaImpl;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioUsuarioImpl;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioUsuarioInsigniaImpl;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioUsuarioInsigniaTest {

    @Autowired
    private SessionFactory sessionfactory;

    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioInsignia repositorioInsignia;
    private ServicioInsignia servicioInsignia;

    @BeforeEach
    public void init() {
        this.repositorioUsuarioInsignia = new RepositorioUsuarioInsigniaImpl(sessionfactory);
        this.repositorioUsuario = new RepositorioUsuarioImpl(sessionfactory);
        this.repositorioInsignia = new RepositorioInsigniaImpl(sessionfactory);
        this.servicioInsignia = new ServicioInsigniaImpl(repositorioUsuarioInsignia);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnaUsuarioInsignia() {

        Usuario usuario = crearUsuario();
        Insignia insignia = crearInsignia();
        repositorioUsuario.guardar(usuario);
        repositorioInsignia.guardar(insignia);

        UsuarioInsignia usuarioInsignia = new UsuarioInsignia();
        usuarioInsignia.setUsuario(usuario);
        usuarioInsignia.setInsignia(insignia);
        repositorioUsuarioInsignia.guardar(usuarioInsignia);


        boolean resultado = servicioInsignia.asignarInsignia(usuario, insignia);

        assertThat(resultado, is(equalTo(true)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerInsigniasDeUnUsuario() {

        Usuario usuario = crearUsuario();
        Insignia insignia1 = crearInsignia();
        Insignia insignia2 = crearInsignia();
        repositorioUsuario.guardar(usuario);
        repositorioInsignia.guardar(insignia1);
        repositorioInsignia.guardar(insignia2);

        UsuarioInsignia usuarioInsignia1 = new UsuarioInsignia();
        usuarioInsignia1.setUsuario(usuario);
        usuarioInsignia1.setInsignia(insignia1);
        repositorioUsuarioInsignia.guardar(usuarioInsignia1);

        UsuarioInsignia usuarioInsignia2 = new UsuarioInsignia();
        usuarioInsignia2.setUsuario(usuario);
        usuarioInsignia2.setInsignia(insignia2);
        repositorioUsuarioInsignia.guardar(usuarioInsignia2);

        List<Insignia> insigniasObtenidas = servicioInsignia.obtenerInsigniasDeUsuario(usuario);

        assertThat(insigniasObtenidas.size(), is(equalTo(2)));
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSePuedaAsignarInsigniaPremiumAUnUsuarioNoPremium() {

        Usuario usuario = crearUsuario();
        Insignia insignia = new InsigniaPremium("Holografica");
        insignia.setNombre("Insignia Premium");
        insignia.setDescripcion("Premium");

        repositorioUsuario.guardar(usuario);
        repositorioInsignia.guardar(insignia);

        boolean resultado = servicioInsignia.asignarInsignia(usuario, insignia);

        assertThat(resultado, is(equalTo(false)));
    }
    

    private Insignia crearInsignia() {
        Insignia insignia = new Insignia();
        insignia.setNombre("Insignia de prueba");
        insignia.setDescripcion("Descripcion de prueba");
        return insignia;
    }

    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("prueba");
        usuario.setEmail("prueba@prueba.com");
        usuario.setPassword("prueba123");
        usuario.setEsPremium(false);
        repositorioUsuario.guardar(usuario);
        return usuario;
    }

}
