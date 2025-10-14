package com.tallerwebi.infraestructura;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.tallerwebi.dominio.Insignia;

public interface RepositorioInsignia {

    void guardar(Insignia insignia);
    Insignia obtenerPorId(Long id);
    List<Insignia> obtenerTodas();
    void eliminar(Insignia insignia);
}
