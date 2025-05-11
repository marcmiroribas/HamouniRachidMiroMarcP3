import prog2.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BitacolaTest {

    private Bitacola bitacola;
    private PaginaEconomica paginaEconomica;
    private PaginaEstat paginaEstat;
    private PaginaIncidencies paginaIncidencies;

    @BeforeEach
    void setUp() {
        bitacola = new Bitacola();

        paginaEconomica = new PaginaEconomica(1, 250.0f, 225.0f, 90.0f,
                225.0f, 0.0f, 215.0f, 10.0f);

        paginaEstat = new PaginaEstat(1, 90.0f, 125.0f, 125.0f, 112.5f, 225.0f);

        paginaIncidencies = new PaginaIncidencies(1);
        paginaIncidencies.afegeixIncidencia("La bomba 2 està fora de servei");
    }

    @Test
    void testConstructor() {
        assertNotNull(bitacola);
        assertTrue(bitacola.getIncidencies().isEmpty());
    }
    x
    @Test
    void testGetIncidencies() {
        bitacola.afegeixPagina(paginaEconomica);
        bitacola.afegeixPagina(paginaIncidencies);
        bitacola.afegeixPagina(paginaEstat);

        PaginaIncidencies otraIncidencia = new PaginaIncidencies(2);
        otraIncidencia.afegeixIncidencia("Reactor sobrecalentat");
        bitacola.afegeixPagina(otraIncidencia);

        List<PaginaIncidencies> incidencias = bitacola.getIncidencies();

        assertEquals(2, incidencias.size());
        assertEquals(paginaIncidencies, incidencias.get(0));
        assertEquals(otraIncidencia, incidencias.get(1));
        assertFalse(incidencias.contains(paginaEconomica));
        assertFalse(incidencias.contains(paginaEstat));
    }

    @Test
    void testToStringEmptyBitacola() {
        String resultado = bitacola.toString();
        assertEquals("", resultado);
    }
}