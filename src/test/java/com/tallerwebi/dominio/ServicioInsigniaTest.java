package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

public class ServicioInsigniaTest {

    Usuario usuario;

    @BeforeEach
    public void init(){

        usuario = new Usuario();
        usuario.setId(1L);
    }

    @Test
    public void dadoQueExisteUnUsuarioSeLePuedeAsignarUnaInsignia() {
        ServicioInsignia servicio = mock(ServicioInsignia.class);

        Insignia insignia = new Insignia();
        insignia.setId(1L);
        insignia.setNombre("Participante");

        when(servicio.asignarInsignia(usuario, insignia)).thenReturn(true);

        boolean seAsigno = servicio.asignarInsignia(usuario, insignia);

        assertThat(seAsigno, equalTo(true));
    }

    @Test
    public void dadoQueExisteUnUsuarioSePuedeObtenerSusInsignias() {
        ServicioInsignia servicio = mock(ServicioInsignia.class);

        Insignia i1 = new Insignia();
        i1.setId(1L);
        i1.setNombre("Participante");

        Insignia i2 = new Insignia();
        i2.setId(2L);
        i2.setNombre("Activo");

        List<Insignia> insigniasMock = new ArrayList<>();
        insigniasMock.add(i1);
        insigniasMock.add(i2);

        when(servicio.obtenerInsigniasDeUsuario(usuario)).thenReturn(insigniasMock);

        List<Insignia> resultado = servicio.obtenerInsigniasDeUsuario(usuario);

        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0).getNombre(), equalTo("Participante"));
        assertThat(resultado.get(1).getNombre(), equalTo("Activo"));
    }

    @Test
    public void dadoQueExisteUnUsuarioSeLePuedeAsignarUnaInsigniaPremium(){
        ServicioInsignia servicio = mock(ServicioInsignia.class);

        usuario.setPremium(true);

        String efecto = "Holografica";
        Insignia insignia = new InsigniaPremium(efecto);
        insignia.setId(2L);

        when(servicio.asignarInsignia(usuario, insignia)).thenReturn(true);

        boolean seAsigno = servicio.asignarInsignia(usuario, insignia);

        assertThat(seAsigno, equalTo(true));
    }

    @Test
    public void dadoQueExisteUnUsuarioNoPremiumNoSeLePuedeAsignarUnaInsigniaPremium(){
        ServicioInsignia servicio = mock(ServicioInsignia.class);

        usuario.setPremium(false);

        String efecto = "Holografica";
        Insignia insignia = new InsigniaPremium(efecto);
        insignia.setId(2L);

        when(servicio.asignarInsignia(usuario, insignia)).thenReturn(false);

        boolean seAsigno = servicio.asignarInsignia(usuario, insignia);

        assertThat(seAsigno, equalTo(false));
    }
}
