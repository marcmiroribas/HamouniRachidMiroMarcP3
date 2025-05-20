package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog2.vista.CentralUBException;

import static org.junit.jupiter.api.Assertions.*;

class SistemaRefrigeracioTest {

    private SistemaRefrigeracio sistema;
    private PaginaIncidencies pagina;
    private BombaRefrigerant bomba1;
    private BombaRefrigerant bomba2;

    static class VariableMock extends VariableUniforme {
        private final float valorForzat;

        public VariableMock(float valorForzat) {
            super(0, 1);
            this.valorForzat = valorForzat;
        }

        @Override
        public float seguentValor() {
            return valorForzat;
        }
    }

    @BeforeEach
    void setUp() {
        sistema = new SistemaRefrigeracio();
        pagina = new PaginaIncidencies(1);

        bomba1 = new BombaRefrigerant(new VariableMock(0.5f), 1); // No falla
        bomba2 = new BombaRefrigerant(new VariableMock(0.1f), 2); // Falla

        sistema.afegirBomba(bomba1);
        sistema.afegirBomba(bomba2);
    }

    @Test
    void testActivaYDesactiva() throws CentralUBException {
        sistema.activa();
        assertTrue(bomba1.getActivat());
        assertFalse(bomba2.getActivat()); // está fora de servei tras revisión

        sistema.desactiva();
        assertFalse(bomba1.getActivat());
        assertFalse(bomba2.getActivat());
    }

    @Test
    void testRevisa() throws CentralUBException {
        sistema.activa(); // activa bomba1
        sistema.revisa(pagina);

        assertTrue(bomba2.getForaDeServei());
        assertFalse(bomba2.getActivat()); // desactivada automáticamente
        assertTrue(pagina.toString().contains("fora de servei"));
    }

    @Test
    void testGetActivat() throws CentralUBException {
        assertFalse(sistema.getActivat());

        sistema.activa();
        assertTrue(sistema.getActivat());

        sistema.desactiva();
        assertFalse(sistema.getActivat());
    }

    @Test
    void testGetCostOperatiu() throws CentralUBException {
        sistema.activa();
        float esperado = bomba1.getCostOperatiu(); // bomba2 está fuera de servicio
        assertEquals(esperado, sistema.getCostOperatiu(), 0.01f);
    }

    @Test
    void testCalculaOutput() throws CentralUBException {
        sistema.activa();
        assertEquals(250.0f, sistema.calculaOutput(500.0f), 0.01f); // solo bomba1 activa

        bomba1.desactiva();
        assertEquals(0.0f, sistema.calculaOutput(500.0f), 0.01f);
    }

    @Test
    void testGetBombaPorId() throws CentralUBException {
        BombaRefrigerant result = sistema.getBomba(1);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetBombaLanzaExcepcion() {
        CentralUBException ex = assertThrows(CentralUBException.class, () -> {
            sistema.getBomba(99);
        });
        assertTrue(ex.getMessage().contains("No s'ha trobat la bomba"));
    }
}
