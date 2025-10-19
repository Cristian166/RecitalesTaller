package com.tallerwebi.dominio.interfacesrepo;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.tallerwebi.dominio.entidades.Recital;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface RepositorioRecitalInterface {

    void guardarRecital(Recital recital);
    void borrarRecital(Recital recital);
    Recital buscarRecitalPorIdRecital(Long id);
    List<Recital> obtenerRecitalesDeUsuario(Usuario usuario);

}
