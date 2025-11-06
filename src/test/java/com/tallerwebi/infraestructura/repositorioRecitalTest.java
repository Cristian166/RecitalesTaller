package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.entidades.Recital;
import com.tallerwebi.dominio.entidades.Usuario;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioRecitalImpl;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioUsuarioImpl;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class repositorioRecitalTest {
    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioRecitalImpl  repositorioRecitalImple;
    private RepositorioUsuarioImpl repositorioUsuarioImple;

    // Inyecto la secion en cada repositorio
    @BeforeEach
    void setupSession() throws Exception {
       this.repositorioRecitalImple = new RepositorioRecitalImpl(sessionFactory);
       this.repositorioUsuarioImple = new RepositorioUsuarioImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void deberiaGuardarUnRecitalConUnUsuario() throws Exception {
        //dado usuario
        Usuario usuario = new Usuario();
        usuario.setEmail("email");
        usuario.setPassword("password");
        usuario.setRol("ROLE_USER");
        repositorioUsuarioImple.guardar(usuario);

        // dado un recital pero asociado a un usuario
        Recital recital = new Recital();
        recital.setNombreRecital("Soda");
        recital.setLocalidad("Rosario");
        recital.setUsuario(usuario);
        repositorioRecitalImple.guardarRecital(recital);

        //cuando busco el recital guardado
        Recital recitalEncontrado = repositorioRecitalImple.buscarRecitalPorIdRecital(recital.getRecitalId());

        // entonces
        assertThat(recitalEncontrado, is(notNullValue()));
        assertThat(recitalEncontrado.getNombreRecital(), equalTo("Soda"));
    }

    @Test
    @Transactional
    public void deberiaBuscarRecitalPorIdRecital() throws Exception {
        // dado usuario
        Usuario usuario = new Usuario();
        usuario.setEmail("email");
        usuario.setPassword("password");
        usuario.setRol("ROLE_USER");
        repositorioUsuarioImple.guardar(usuario);

        // dado recital
        Recital recital = new Recital();
        recital.setNombreRecital("Soda 2");
        recital.setLocalidad("Bs As");
        recital.setUsuario(usuario);
        repositorioRecitalImple.guardarRecital(recital);

        Recital recitalPersistido =  repositorioRecitalImple.buscarRecitalPorIdRecital(recital.getRecitalId());

        assertThat(recital.getRecitalId(), is(notNullValue()));
        assertThat(recital.getNombreRecital(), equalTo("Soda 2"));
    }

    @Test
    @Transactional
    public void deberiaBuscarTodoslosRecitalesDeUnUsuario() throws Exception {
        // dado un usuario
        Usuario usuario = new Usuario();
        usuario.setEmail("email");
        usuario.setPassword("password");
        usuario.setRol("ROLE_USER");
        repositorioUsuarioImple.guardar(usuario);

        // dado que tiene 2 recitales
        Recital recital1 = new Recital();
        recital1.setNombreRecital("Soda 1");
        recital1.setLocalidad("Rosario");
        recital1.setUsuario(usuario);
        repositorioRecitalImple.guardarRecital(recital1);

        Recital recital2 = new Recital();
        recital2.setNombreRecital("Soda 2");
        recital2.setLocalidad("Bs As");
        recital2.setUsuario(usuario);
        repositorioRecitalImple.guardarRecital(recital2);

        List<Recital> recitalPersistido = repositorioRecitalImple.obtenerRecitalesDeUsuario(usuario);
        int largo = recitalPersistido.size();

        assertThat(largo, is(equalTo(2)));
    }


    @Test
    @Transactional
    public void deberiaEliminarRecitalPorIdRecital() throws Exception {
        // dado un usuario
        Usuario usuario = new Usuario();
        usuario.setEmail("email");
        usuario.setPassword("password");
        usuario.setRol("ROLE_USER");
        repositorioUsuarioImple.guardar(usuario);

        // dado que tiene 2 recitales
        Recital recital1 = new Recital();
        recital1.setNombreRecital("Soda 1");
        recital1.setLocalidad("Rosario");
        recital1.setUsuario(usuario);
        repositorioRecitalImple.guardarRecital(recital1);

        Recital recital2 = new Recital();
        recital2.setNombreRecital("Soda 2");
        recital2.setLocalidad("Bs As");
        recital2.setUsuario(usuario);
        repositorioRecitalImple.guardarRecital(recital2);

        List<Recital> recitalPersistido = repositorioRecitalImple.obtenerRecitalesDeUsuario(usuario);
        int largo = recitalPersistido.size();

        //entonces tengo dos recitales
        assertThat(largo, is(equalTo(2)));

        //Pero ahora borro 1 recital
        repositorioRecitalImple.borrarRecital(recital1);
        List<Recital> recitalPersistidoLuegoDeAliminar =  repositorioRecitalImple.obtenerRecitalesDeUsuario(usuario);
        int largoLuegoDeAliminar = recitalPersistidoLuegoDeAliminar.size();

        assertThat(largoLuegoDeAliminar, is(equalTo(1)));

    }
}
