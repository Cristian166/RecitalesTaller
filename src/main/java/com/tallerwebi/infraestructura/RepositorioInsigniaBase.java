package com.tallerwebi.infraestructura;

import java.util.List;
import com.tallerwebi.dominio.Insignia;

public interface RepositorioInsigniaBase {
    void guardar(Insignia insignia);
    void modificar(Insignia insignia);
    Insignia buscar(Long id);
    List<Insignia> listarTodas();
}
