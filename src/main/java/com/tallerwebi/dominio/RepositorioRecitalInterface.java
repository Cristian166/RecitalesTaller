package com.tallerwebi.dominio;

import org.springframework.orm.ObjectRetrievalFailureException;

import java.util.List;

public interface RepositorioRecitalInterface {

    void guardarRecital(Recital recital);
    void borrarRecital(Recital recital);
    Recital buscarRecitalPorIdRecital(Long id);
    List<Recital> obtenerRecitalesDeUsuario(Usuario usuario);

}
