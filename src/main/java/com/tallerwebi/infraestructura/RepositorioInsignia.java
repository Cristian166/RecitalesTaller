package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.Insignia;

public interface RepositorioInsignia {

    Boolean guardar(Insignia insignia);
    Insignia obtenerPorId(Long id);
    List<Insignia> obtenerTodas();
    void eliminar(Insignia insignia);
}
