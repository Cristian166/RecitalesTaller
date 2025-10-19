package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Insignia;

public interface RepositorioInsignia {

    Long guardar(Insignia insignia);
    Insignia obtenerPorId(Long id);
    List<Insignia> obtenerTodas();
    void eliminar(Insignia insignia);
}
