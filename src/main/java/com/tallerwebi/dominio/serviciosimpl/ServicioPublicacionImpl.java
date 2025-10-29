package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Publicacion;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioComunidad;
import com.tallerwebi.infraestructura.RepositorioPublicacion;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ServicioPublicacionImpl implements ServicioPublicacion {

    @Autowired
    private RepositorioPublicacion repositorioPublicacion;

    @Autowired
    private RepositorioComunidad repositorioComunidad;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private HttpSession session;

    @Override
    @Transactional
    public void crearPublicacion(Publicacion publicacion, Long comunidadId) {

        Comunidad comunidad = repositorioComunidad.obtenerPorId(comunidadId);
        if(comunidad != null){

            String email = (String) session.getAttribute("usuario_email");

            if(email != null){
                Usuario usuario = repositorioUsuario.buscar(email);

                if(usuario != null){
                    publicacion.setAutorPublicacion(usuario);

                    publicacion.setComunidad(comunidad);

                    repositorioPublicacion.guardar(publicacion);
                }
            }
        }
    }

    @Override
    public List<Publicacion> listarPublicacionesPorComunidad(Long comunidadId) {
        return repositorioPublicacion.obtenerPorComunidad(comunidadId);
    }
}
