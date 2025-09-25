package com.tallerwebi.presentacion;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.ServicioComentario;
import com.tallerwebi.dominio.Usuario;

class ControladorComentarioTest {

    @Mock
    private ServicioComentario servicio;

    @InjectMocks
    private ControladorComentario controlador;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setEmail("juan@mail.com");
        usuario.setActivo(true);
    }

    @Test
    void comentarConDatosValidosDeberiaGuardarYListar() {
        Comentario comentario = new Comentario(usuario, "Hola a todos!");
        List<Comentario> listaMock = Arrays.asList(comentario);
        when(servicio.listarComentarios()).thenReturn(listaMock);

        ModelAndView mav = controlador.comentar(comentario, null);

        assertEquals("foro-comentarios", mav.getViewName());
        assertEquals(listaMock, mav.getModel().get("comentarios"));
        verify(servicio).crearComentario(usuario, "Hola a todos!");
        verify(servicio).listarComentarios();
    }

    @Test
    void mostrarComentariosDeberiaRetornarVistaConLista() {
        Comentario comentario = new Comentario(usuario, "Probando el foro");
        List<Comentario> listaMock = Arrays.asList(comentario);
        when(servicio.listarComentarios()).thenReturn(listaMock);

        ModelAndView mav = controlador.mostrarComentarios();

        assertEquals("foro-comentarios", mav.getViewName());
        assertEquals(listaMock, mav.getModel().get("comentarios"));
        verify(servicio).listarComentarios();
    }

}
