package com.tallerwebi.dominio.serviciosimpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.ServicioEncuesta;
import com.tallerwebi.dominio.ServicioInsignia;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.dominio.entidades.Insignia;
import com.tallerwebi.dominio.entidades.OpcionEncuesta;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.Voto;
import com.tallerwebi.infraestructura.RepositorioEncuesta;
import com.tallerwebi.infraestructura.RepositorioInsignia;
import com.tallerwebi.infraestructura.RepositorioOpcionEncuesta;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioInsignia;
import com.tallerwebi.infraestructura.RepositorioVoto;

@Service
public class ServicioEncuestaImpl implements ServicioEncuesta {

    private RepositorioEncuesta repoEncuesta;
    private RepositorioOpcionEncuesta repoOpciones;
    private RepositorioVoto repoVoto;

    private ServicioInsignia servicioInsignia;
    private RepositorioInsignia repositorioInsignia;
    private RepositorioUsuarioInsignia repositorioUsuarioInsignia;
    private RepositorioUsuario repositorioUsuario;

    public ServicioEncuestaImpl(RepositorioEncuesta repoEncuesta,
            RepositorioOpcionEncuesta repoOpciones,
            RepositorioVoto repoVoto,
            ServicioInsignia servicioInsignia,
            RepositorioInsignia repositorioInsignia,
            RepositorioUsuario repositorioUsuario,
            RepositorioUsuarioInsignia repositorioUsuarioInsignia) {
        this.repoEncuesta = repoEncuesta;
        this.repoOpciones = repoOpciones;
        this.repoVoto = repoVoto;
        this.servicioInsignia = servicioInsignia;
        this.repositorioInsignia = repositorioInsignia;
        this.repositorioUsuarioInsignia = repositorioUsuarioInsignia;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    @Transactional
    public Encuesta obtenerEncuestaActiva(Long comunidadId) {
        return repoEncuesta.obtenerEncuestaActiva(comunidadId);
    }

    @Override
    @Transactional
    public Encuesta crearEncuesta(Comunidad comunidad, Usuario creador, String pregunta, List<String> opciones) {
        Encuesta activa = repoEncuesta.obtenerEncuestaActiva(comunidad.getId());
        if (activa != null) {
            activa.setActiva(false);
            repoEncuesta.guardar(activa);
        }

        Encuesta encuesta = new Encuesta();
        encuesta.setPregunta(pregunta);
        encuesta.setComunidad(comunidad);
        encuesta.setCreador(creador);
        encuesta.setActiva(true);

        repoEncuesta.guardar(encuesta);

        for (String texto : opciones) {
            OpcionEncuesta opcion = new OpcionEncuesta();
            opcion.setTexto(texto);
            opcion.setEncuesta(encuesta);
            repoOpciones.guardar(opcion);
        }
        Usuario usuarioBuscado = repositorioUsuario.buscarId(creador.getId());
        boolean yaTieneInsignia = repositorioUsuarioInsignia.existe(usuarioBuscado.getId(), 6L);

        if (!yaTieneInsignia) {
            Insignia insigniaEncuestador = repositorioInsignia.obtenerPorId(6L);
            if (insigniaEncuestador != null) {
                servicioInsignia.asignarInsignia(usuarioBuscado, insigniaEncuestador);
            }

        }

        return encuesta;
    }

    @Override
    @Transactional
    public Boolean votar(Long opcionId, Usuario usuario) {
        OpcionEncuesta opcion = repoOpciones.obtenerPorId(opcionId);
        if (opcion == null)
            return false;

        Long encuestaId = opcion.getEncuesta().getId();

        Voto yaVoto = repoVoto.obtenerVotoDeUsuario(usuario.getId(), encuestaId);
        if (yaVoto != null) {
            OpcionEncuesta opcionAnterior = yaVoto.getOpcion();
            opcionAnterior.setVotos(opcionAnterior.getVotos() - 1);
            repoOpciones.guardar(opcionAnterior);

            repoVoto.eliminar(yaVoto);
        }

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        voto.setOpcion(opcion);

        opcion.setVotos(opcion.getVotos() + 1);

        repoVoto.guardar(voto);
        repoOpciones.guardar(opcion);
        return true;
    }

    @Override
    @Transactional
    public Boolean eliminarEncuesta(Long encuestaId) {
        Encuesta encuesta = repoEncuesta.obtenerPorId(encuestaId);
        if (encuesta != null) {
            repoEncuesta.eliminar(encuesta.getId());
            return true;
        }
        return false;
    }

}
