package com.tallerwebi.infraestructura;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioEntradaImpl;
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioPerfilImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioEntradaTest {
    
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEntrada repositorio;

    @BeforeEach
    public void init() {
        this.repositorio = new RepositorioEntradaImpl(sessionFactory);
    }


    @Test
    @Transactional
    @Rollback
    public void debePoderGuardar1EntradaYAsignarlaAUnUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario);

        Entrada entrada1 = new Entrada();
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");

        repositorio.guardarEntradaPorUsuario(entrada1, usuario);
        List<Entrada> entradas = repositorio.obtenerEntradasPorUsuario(usuario);

        assertEquals(1, entradas.size());
    }

    @Test
    @Transactional
    @Rollback
    public void debePoderObtenerLasEntradasQueTieneCadaUsuario(){

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Alan");
        sessionFactory.getCurrentSession().save(usuario2);

        Entrada entrada1 = new Entrada();
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");

        Entrada entrada2 = new Entrada();
        entrada2.setNombreRecital("Duki");
        entrada2.setLugar("Movistar");
        entrada2.setFecha(LocalDate.parse("2026-01-15"));
        entrada2.setHorario("21:00");
        entrada2.setSeccion("Campo");

        Entrada entrada3 = new Entrada();
        entrada3.setNombreRecital("YSY A");
        entrada3.setLugar("Movistar");
        entrada3.setFecha(LocalDate.parse("2026-01-15"));
        entrada3.setHorario("21:00");
        entrada3.setSeccion("Campo");

        repositorio.guardarEntradaPorUsuario(entrada1, usuario1);
        repositorio.guardarEntradaPorUsuario(entrada2, usuario1);

        repositorio.guardarEntradaPorUsuario(entrada3, usuario2);

        List<Entrada> entradasUsuario1 = repositorio.obtenerEntradasPorUsuario(usuario1);
        List<Entrada> entradasUsuario2 = repositorio.obtenerEntradasPorUsuario(usuario2);

        assertEquals(2, entradasUsuario1.size());
        assertEquals(1, entradasUsuario2.size());
    }


    @Test
    @Transactional
    @Rollback
    public void debePoderEliminarUnaEntradaConElId(){

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario1);

        Entrada entrada1 = new Entrada();
        entrada1.setNombreRecital("Dark Tranquility");
        entrada1.setLugar("El teatrito");
        entrada1.setFecha(LocalDate.parse("2026-01-15"));
        entrada1.setHorario("21:00");
        entrada1.setSeccion("Campo");


        Entrada entrada2 = new Entrada();
        entrada2.setNombreRecital("Duki");
        entrada2.setLugar("Movistar");
        entrada2.setFecha(LocalDate.parse("2026-01-15"));
        entrada2.setHorario("21:00");
        entrada2.setSeccion("Campo");

        repositorio.guardarEntradaPorUsuario(entrada1, usuario1);
        repositorio.guardarEntradaPorUsuario(entrada2, usuario1);

        List<Entrada> entradasUsuario1 = repositorio.obtenerEntradasPorUsuario(usuario1);
        assertEquals(2, entradasUsuario1.size());
        
        repositorio.eliminarPorId(entrada1.getId());
        entradasUsuario1 = repositorio.obtenerEntradasPorUsuario(usuario1);
        assertEquals(1, entradasUsuario1.size());
    }
}
