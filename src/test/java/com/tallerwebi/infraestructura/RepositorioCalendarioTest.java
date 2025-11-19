package com.tallerwebi.infraestructura;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.tallerwebi.infraestructura.repositorioImpl.RepositorioCalendarioImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioCalendarioTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCalendario repositorioCalendario;

    @BeforeEach
    public void init() {
        this.repositorioCalendario = new RepositorioCalendarioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void deboPoderObtenerLosEventosDelUsuario(){
    
        Usuario usuario = new Usuario();
        usuario.setNombre("Nico");
        sessionFactory.getCurrentSession().save(usuario);

 
        Entrada entrada1 = new Entrada();
        entrada1.setFecha(LocalDate.now());
        entrada1.setUsuario(usuario);

        Entrada entrada2 = new Entrada();
        entrada2.setFecha(LocalDate.of(2025,11 ,14 ));
        entrada2.setUsuario(usuario);

        sessionFactory.getCurrentSession().save(entrada1);
        sessionFactory.getCurrentSession().save(entrada2);

        List<Entrada> resultado = repositorioCalendario.obtenerEventos(usuario);

        assertEquals(2, resultado.size());

        assertTrue(resultado.contains(entrada1));
        assertTrue(resultado.contains(entrada2));
    }

}
