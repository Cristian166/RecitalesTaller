package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Recital;
import com.tallerwebi.dominio.ServicioVisitasImp;
import com.tallerwebi.dominio.ServicioVisitasInterfaz;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class ServicioVisitasTest {
    @Test
    public void testObtenerRecitalesLatitud() {
        // Preparación
        ServicioVisitasInterfaz servicio = new ServicioVisitasImp();

        // Ejecución
        List<Recital> recitales = servicio.obtenerRecitales();

        // Aserción: cada recital debe tener latitud que empiece con -34
        for (Recital r : recitales) {
            int latInt = (int) r.getLatitud();
            assertTrue( latInt == -34,
                    "La latitud debe comenzar con -34 para el recital: " + r.getNombre());
        }
    }

}
