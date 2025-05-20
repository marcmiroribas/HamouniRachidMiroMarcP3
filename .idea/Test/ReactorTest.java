package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import prog2.vista.CentralUBException;

public class ReactorTest {

    private Reactor reactor;
    private PaginaIncidencies paginaIncidencies;

    @BeforeEach
    public void setUp() {
        reactor = new Reactor();
        paginaIncidencies = new PaginaIncidencies(1);
    }

    @Test
    public void testConstructor() {
        assertAll(
                () -> assertFalse(reactor.getActivat()),
                () -> assertEquals(25, reactor.getTemperatura(), 0.001f),
                () -> assertEquals(100, reactor.getInsercioBarres(), 0.001f),
                () -> assertFalse(reactor.getForaDeServei())
        );
    }

    @Test
    public void testActivaSuccess() throws CentralUBException {
        reactor.setInsercioBarres(50);
        reactor.activa();
        assertTrue(reactor.getActivat());
    }

    @Test
    public void testActivaWhenForaDeServei() {
        reactor.setForaDeServei(true);
        assertThrows(CentralUBException.class, () -> reactor.activa());
    }

    @Test
    public void testActivaWhenHighTemperature() {
        reactor.setTemperatura(1001);
        assertThrows(CentralUBException.class, () -> reactor.activa());
    }

    @Test
    public void testActivaWhenLowBarres() {
        reactor.setInsercioBarres(19);
        assertThrows(CentralUBException.class, () -> reactor.activa());
    }

    @Test
    public void testDesactiva() throws CentralUBException {
        reactor.activa();
        reactor.desactiva();
        assertAll(
                () -> assertFalse(reactor.getActivat()),
                () -> assertEquals(25, reactor.getTemperatura(), 0.001f)
        );
    }

    @Test
    public void testCalculaOutputWhenActivat() throws CentralUBException {
        reactor.setInsercioBarres(50);
        reactor.activa();
        float input = 1000;
        float expectedOutput = (100 - 50) * input / 100;
        assertEquals(expectedOutput, reactor.calculaOutput(input), 0.001f);
    }

    @Test
    public void testCalculaOutputWhenDesactivat() {
        assertEquals(25, reactor.calculaOutput(1000), 0.001f);
    }

    @Test
    public void testCalculaOutputWhenForaDeServei() {
        reactor.setForaDeServei(true);
        assertEquals(25, reactor.calculaOutput(1000), 0.001f);
    }

    @Test
    public void testGetCostOperatiuWhenActivat() throws CentralUBException {
        reactor.activa();
        assertEquals(35, reactor.getCostOperatiu(), 0.001f);
    }

    @Test
    public void testGetCostOperatiuWhenDesactivat() {
        assertEquals(0, reactor.getCostOperatiu(), 0.001f);
    }

    @Test
    public void testGetCostOperatiuWhenForaDeServei() throws CentralUBException {
        reactor.activa();
        reactor.setForaDeServei(true);
        assertEquals(0, reactor.getCostOperatiu(), 0.001f);
    }

    @Test
    public void testRevisaCriticalTemperature() {
        reactor.setTemperatura(1001);
        reactor.revisa(paginaIncidencies);
        assertAll(
                () -> assertTrue(reactor.getForaDeServei()),
                () -> assertFalse(reactor.getActivat()),
                () -> assertEquals(1, paginaIncidencies.getLlistaIncidencies().size()),
                () -> assertTrue(paginaIncidencies.toString().contains("Temperatura crítica"))
        );
    }

    @Test
    public void testRevisaLowBarres() throws CentralUBException {
        reactor.setInsercioBarres(4);
        reactor.activa();
        reactor.revisa(paginaIncidencies);
        assertAll(
                () -> assertEquals(1, paginaIncidencies.getLlistaIncidencies().size()),
                () -> assertTrue(paginaIncidencies.toString().contains("Inserció de barres massa baixa"))
        );
    }

    @Test
    public void testRevisaNoIssues() {
        reactor.revisa(paginaIncidencies);
        assertTrue(paginaIncidencies.getLlistaIncidencies().isEmpty());
    }

    @Test
    public void testSetTemperatura() {
        reactor.setTemperatura(500);
        assertEquals(500, reactor.getTemperatura(), 0.001f);
    }

    @Test
    public void testSetInsercioBarres() {
        reactor.setInsercioBarres(50);
        assertEquals(50, reactor.getInsercioBarres(), 0.001f);
    }

    @Test
    public void testToString() {
        String str = reactor.toString();
        assertAll(
                () -> assertTrue(str.contains("Reactor")),
                () -> assertTrue(str.contains("activat=false")),
                () -> assertTrue(str.contains("temperatura=25")),
                () -> assertTrue(str.contains("insercioBarres=100"))
        );
    }

    // Método helper para acceder al campo foraDeServei (no es parte de la interfaz pública)
    private void setForaDeServei(boolean value) {
        reactor.setForaDeServei(value);
    }
}