package com.tallerwebi.TDD;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.ServicioComentario;
import com.tallerwebi.dominio.ServicioComentarioImpl;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioComentarioTest {

    private ServicioComentario servicio;
    private Usuario usuario;

    @BeforeEach
    public void init() {
        servicio = new ServicioComentarioImpl();
        usuario = new Usuario();
        usuario.setEmail("sofi@mail.com");
        usuario.setActivo(true);
    }

    @Test
    public void deberiaCrearUnComentario() {
        Comentario c = servicio.crearComentario(usuario, "Hola comunidad!");
        assertNotNull(c);
        assertEquals(usuario, c.getUsuario());
        assertEquals("Hola comunidad!", c.getTexto());
    }

    @Test
    public void deberiaGuardarUnComentarioEnLaLista() {
        servicio.crearComentario(usuario, "primer comentario");

        List<Comentario> lista = servicio.listarComentarios();

        assertEquals(1, lista.size());
        assertEquals(usuario, lista.get(0).getUsuario());
        assertEquals("primer comentario", lista.get(0).getTexto());
    }

    @Test
    public void deberiaListarVariosComentarios() {
        servicio.crearComentario(usuario, "hola!");
        Usuario juan = new Usuario();
        juan.setEmail("juan@mail.com");
        servicio.crearComentario(juan, "buenas tardes");
        Usuario maria = new Usuario();
        maria.setEmail("maria@mail.com");
        servicio.crearComentario(maria, "todo bien?");

        List<Comentario> lista = servicio.listarComentarios();

        assertEquals(3, lista.size());
        assertEquals("hola!", lista.get(0).getTexto());
        assertEquals("buenas tardes", lista.get(1).getTexto());
        assertEquals("todo bien?", lista.get(2).getTexto());
    }
}
