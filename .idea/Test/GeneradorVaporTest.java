package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import prog2.vista.CentralUBException;

public class GeneradorVaporTest {

    private GeneradorVapor generadorVapor;
    private PaginaIncidencies paginaIncidencies;

    @BeforeEach
    public void setUp() {
        generadorVapor = new GeneradorVapor();
        paginaIncidencies = new PaginaIncidencies(1); // Dia 1 para las pruebas
    }

    @Test
    public void testConstructor() {
        assertFalse(generadorVapor.getActivat(),
                "El generador de vapor debería crearse desactivado");
    }

    @Test
    public void testActiva() {
        generadorVapor.activa();
        assertTrue(generadorVapor.getActivat(),
                "El generador debería estar activado después de llamar a activa()");
    }

    @Test
    public void testDesactiva() {
        generadorVapor.activa(); // Primero lo activamos
        generadorVapor.desactiva();
        assertFalse(generadorVapor.getActivat(),
                "El generador debería estar desactivado después de llamar a desactiva()");
    }

    @Test
    public void testGetCostOperatiuWhenActivat() {
        generadorVapor.activa();
        assertEquals(25f, generadorVapor.getCostOperatiu(), 0.001f,
                "El coste operativo debería ser 25 cuando está activado");
    }

    @Test
    public void testGetCostOperatiuWhenDesactivat() {
        generadorVapor.desactiva();
        assertEquals(0f, generadorVapor.getCostOperatiu(), 0.001f,
                "El coste operativo debería ser 0 cuando está desactivado");
    }

    @Test
    public void testCalculaOutputWhenActivat() {
        generadorVapor.activa();
        float input = 100f;
        float expectedOutput = input * 0.9f;
        assertEquals(expectedOutput, generadorVapor.calculaOutput(input), 0.001f,
                "El output debería ser el 90% del input cuando está activado");
    }

    @Test
    public void testCalculaOutputWhenDesactivat() {
        generadorVapor.desactiva();
        assertEquals(0f, generadorVapor.calculaOutput(100f), 0.001f,
                "El output debería ser 0 cuando está desactivado");
    }

    @Test
    public void testRevisa() {
        // El generador de vapor no debe generar incidencias
        int initialSize = paginaIncidencies.getLlistaIncidencies().size();
        generadorVapor.revisa(paginaIncidencies);
        assertEquals(initialSize, paginaIncidencies.getLlistaIncidencies().size(),
                "Revisar el generador no debería añadir incidencias");
    }

    @Test
    public void testMultipleActivations() {
        generadorVapor.activa();
        generadorVapor.activa(); // Activación múltiple
        assertTrue(generadorVapor.getActivat(),
                "El generador debería permanecer activado después de múltiples activaciones");
    }

    @Test
    public void testMultipleDeactivations() {
        generadorVapor.desactiva();
        generadorVapor.desactiva(); // Desactivación múltiple
        assertFalse(generadorVapor.getActivat(),
                "El generador debería permanecer desactivado después de múltiples desactivaciones");
    }

    @Test
    public void testStateTransitions() {
        // Prueba de transiciones de estado
        generadorVapor.activa();
        assertTrue(generadorVapor.getActivat());

        generadorVapor.desactiva();
        assertFalse(generadorVapor.getActivat());

        generadorVapor.activa();
        assertTrue(generadorVapor.getActivat());
    }
}