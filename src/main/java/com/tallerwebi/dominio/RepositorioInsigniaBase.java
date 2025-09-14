package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioInsigniaBase {
    void guardar(Insignia insignia);
    void modificar(Insignia insignia);
    Insignia buscar(Long id);
    List<Insignia> listarTodas();
}
