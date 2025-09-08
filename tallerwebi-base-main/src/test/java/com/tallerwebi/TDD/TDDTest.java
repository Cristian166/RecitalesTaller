package com.tallerwebi.TDD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import org.junit.jupiter.api.Test;

public class TDDTest {

    @Test
    public void debeDevolverInvalidaCuandoTieneMenosDe8Caracteres() {

        String contrasena = "abc";

        String fortaleza = TDD.validarFortalezaContrasena(contrasena);
        assertThat(fortaleza, equalToIgnoringCase("INVALIDA"));
    }

    @Test
    public void debeDevolverDebilCuandoTieneAlMenos8Caracteres() {

        String contrasena = "abcdefgh";

        String fortaleza = TDD.validarFortalezaContrasena(contrasena);
        assertThat(fortaleza, equalToIgnoringCase("DEBIL"));
    }

    @Test
    public void debeDevolverMedianaCuandoTiene8CaracteresY1CaracterEspecial() {

        String contrasena = "abcdefgh@";

        String fortaleza = TDD.validarFortalezaContrasena(contrasena);
        assertThat(fortaleza, equalToIgnoringCase("MEDIANA"));
    }

    @Test
    public void debeDevolverFuerteCuandoTiene8Caracteres1CaracterEspecialY4Numeros() {

        String contrasena = "1234@abc@";

        String fortaleza = TDD.validarFortalezaContrasena(contrasena);
        assertThat(fortaleza, equalToIgnoringCase("FUERTE"));
    }

    @Test
    public void debeDevolverCongelanteSiEsIgualOMenorA0Grados(){
        Integer temperatura = -3;
        String temperaturaCalificada = TDD.clasificarTemperatura(temperatura);

        assertThat(temperaturaCalificada, equalToIgnoringCase("CONGELANTE"));
    }
}
