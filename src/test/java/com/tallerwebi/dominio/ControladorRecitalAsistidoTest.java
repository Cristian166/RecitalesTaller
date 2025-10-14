package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ControladorRecitalAsistido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorRecitalAsistidoTest{

    private ServicioRecital ResitalServicioMkt;
    private ControladorRecitalAsistido ControladorRecitalAsistido;

    @BeforeEach
    public void setUp(){
        ResitalServicioMkt = mock(ServicioRecital.class);
        ControladorRecitalAsistido = new ControladorRecitalAsistido(ResitalServicioMkt); // solo tengo que mokear sus dependencia
    }

    @Test
    public void debeRetornarTrueSiElUsuarioConsultadoNoposeeRecitalesAsistido() throws Exception{

        //dado
        long usuarioId = 43;
        when(ResitalServicioMkt.obtenerRecitalesAsistidosPorUsuario(usuarioId)).thenReturn(Collections.emptyList());
        //Cuando
        boolean resultado = ControladorRecitalAsistido.usuariosNoPoseeRecitalAsistido(usuarioId);

        //Entonces
        assertTrue(resultado);

    }

    @Test
    public void debeRetornarTrueSiElUsuarioConsultadoPoseeRecitalesAsistido() throws Exception{
        long usuarioId = 43;
        //dado
        when(ResitalServicioMkt.obtenerRecitalesAsistidosPorUsuario(usuarioId)).thenReturn(Collections.emptyList());
        //cuando
        boolean resultado = ControladorRecitalAsistido.usuariosSiPoseeRecitalAsistido(usuarioId);
        //entonces
        assertTrue(resultado);
    }
}
