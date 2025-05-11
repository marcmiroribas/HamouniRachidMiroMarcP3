import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import prog2.model.*;
import prog2.vista.*;


public class BombaRefrigerantTest {

    // Clase mock per VariableUniforme per controlar els valors de la prova
    class MockVariableUniforme extends VariableUniforme {
        private float nextValue;

        public MockVariableUniforme(float min, float max) {
            super(min, max);
        }

        public void setNextValue(float value) {
            this.nextValue = value;
        }

        @Override
        public float seguentValor() {
            return nextValue;
        }
    }

    @Test
    public void testConstructor() {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        assertEquals(1, bomba.getId());
        assertFalse(bomba.getActivat());
        assertFalse(bomba.getForaDeServei());
    }

    @Test
    public void testActiva() throws CentralUBException {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        bomba.activa();
        assertTrue(bomba.getActivat());
    }

    @Test
    public void testActivaWhenForaDeServei() {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);
        bomba.foraDeServei = true;

        assertThrows(CentralUBException.class, () -> bomba.activa());
        assertFalse(bomba.getActivat());
    }

    @Test
    public void testDesactiva() throws CentralUBException {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        bomba.activa();
        bomba.desactiva();
        assertFalse(bomba.getActivat());
    }

    @Test
    public void testRevisaWhenForaDeServei() {
        MockVariableUniforme vu = new MockVariableUniforme(0, 1);
        vu.setNextValue(0.1f); // Valor < 0.25 para que falle
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);
        PaginaIncidencies p = new PaginaIncidencies();

        bomba.revisa(p);
        assertTrue(bomba.getForaDeServei());
        assertFalse(bomba.getActivat());
        assertTrue(p.toString().contains("La bomba refrigerant 1 està fora de servei"));
    }

    @Test
    public void testRevisaWhenOperativa() {
        MockVariableUniforme vu = new MockVariableUniforme(0, 1);
        vu.setNextValue(0.3f); // Valor > 0.25 para que no falle
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);
        PaginaIncidencies p = new PaginaIncidencies();

        bomba.revisa(p);
        assertFalse(bomba.getForaDeServei());
        assertTrue(p.getLlistaIncidencies().isEmpty());
    }

    @Test
    public void testGetCapacitatWhenActiva() throws CentralUBException {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        bomba.activa();
        assertEquals(250.0f, bomba.getCapacitat());
    }

    @Test
    public void testGetCapacitatWhenInactiva() {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        assertEquals(0.0f, bomba.getCapacitat());
    }

    @Test
    public void testGetCostOperatiuWhenActiva() throws CentralUBException {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        bomba.activa();
        assertEquals(130.0f, bomba.getCostOperatiu());
    }

    @Test
    public void testGetCostOperatiuWhenInactiva() {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        assertEquals(0.0f, bomba.getCostOperatiu());
    }

    @Test
    public void testToString() {
        VariableUniforme vu = new VariableUniforme(0, 1);
        BombaRefrigerant bomba = new BombaRefrigerant(vu, 1);

        String expected = "Id=1, Activat=false, Fora de servei=false";
        assertEquals(expected, bomba.toString());
    }
}