package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurbinaTest {

    private Turbina turbina;
    private PaginaIncidencies pagina;

    @BeforeEach
    void setUp() {
        turbina = new Turbina();
        pagina = new PaginaIncidencies(1);
    }

    @Test
    void testInicialmenteDesactivada() {
        assertFalse(turbina.getActivat());
        assertEquals(0f, turbina.getCostOperatiu(), 0.001f);
        assertEquals(0f, turbina.calculaOutput(150f), 0.001f);
    }

    @Test
    void testActivaYDesactiva() {
        turbina.activa();
        assertTrue(turbina.getActivat());

        turbina.desactiva();
        assertFalse(turbina.getActivat());
    }

    @Test
    void testCostOperatiu() {
        assertEquals(0f, turbina.getCostOperatiu(), 0.001f);
        turbina.activa();
        assertEquals(20f, turbina.getCostOperatiu(), 0.001f);
    }

    @Test
    void testCalculaOutput() {
        turbina.activa();

        // Output solo si input >= 100
        assertEquals(200f, turbina.calculaOutput(100f), 0.001f);
        assertEquals(300f, turbina.calculaOutput(150f), 0.001f);

        // Si input < 100 → 0 output
        assertEquals(0f, turbina.calculaOutput(99f), 0.001f);
    }

    @Test
    void testRevisaNoHaceNada() {
        turbina.revisa(pagina);
        // Como no hay incidencias, simplemente se asegura que no lanza errores
        assertTrue(pagina.toString().isEmpty());
    }
}
