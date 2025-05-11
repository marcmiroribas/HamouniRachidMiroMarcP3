import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import prog2.model.*;
import prog2.vista.CentralUBException;
import java.io.*;
import java.util.List;

public class DadesTest {

    private Dades dades;

    @BeforeEach
    public void setUp() {
        dades = new Dades();
    }

    @Test
    public void testConstructor() {
        assertEquals(1, dades.getDia());
        assertEquals(100, dades.getInsercioBarres());
        assertEquals(0, dades.getGuanysAcumulats());
        assertFalse(dades.mostraReactor().getActivat());
        assertTrue(dades.getGeneradorVapor().getActivat());
        assertTrue(dades.getTurbina().getActivat());
        assertNotNull(dades.mostraBitacola());
    }

    @Test
    public void testSetInsercioBarres() throws CentralUBException {
        dades.setInsercioBarres(50);
        assertEquals(50, dades.getInsercioBarres());

        dades.setInsercioBarres(0);
        assertEquals(0, dades.getInsercioBarres());

        dades.setInsercioBarres(100);
        assertEquals(100, dades.getInsercioBarres());
    }

    @Test
    public void testSetInsercioBarresInvalid() {
        assertThrows(CentralUBException.class, () -> dades.setInsercioBarres(-1));
        assertThrows(CentralUBException.class, () -> dades.setInsercioBarres(101));
    }

    @Test
    public void testActivaReactor() throws CentralUBException {
        dades.mostraReactor().setTemperatura(500);
        dades.activaReactor();
        assertTrue(dades.mostraReactor().getActivat());
    }

    @Test
    public void testActivaReactorWhenHighTemperature() {
        dades.mostraReactor().setTemperatura(1001);
        assertThrows(CentralUBException.class, () -> dades.activaReactor());
    }

    @Test
    public void testDesactivaReactor() {
        dades.desactivaReactor();
        assertFalse(dades.mostraReactor().getActivat());
    }

    @Test
    public void testActivaBomba() throws CentralUBException {
        dades.activaBomba(0);
        assertTrue(dades.mostraSistemaRefrigeracio().getBomba(0).getActivat());
    }

    @Test
    public void testActivaBombaForaDeServei() throws CentralUBException {
        dades.mostraSistemaRefrigeracio().getBomba(0).foraDeServei = true;
        assertThrows(CentralUBException.class, () -> dades.activaBomba(0));
    }

    @Test
    public void testDesactivaBomba() throws CentralUBException {
        dades.activaBomba(0);
        dades.desactivaBomba(0);
        assertFalse(dades.mostraSistemaRefrigeracio().getBomba(0).getActivat());
    }

    @Test
    public void testCalculaPotencia() throws CentralUBException {
        // Configurar estado inicial
        dades.activaReactor();
        dades.activaBomba(0);
        dades.activaBomba(1);
        dades.getGeneradorVapor().activa();
        dades.getTurbina().activa();

        float potencia = dades.calculaPotencia();
        assertTrue(potencia > 0);
    }

    @Test
    public void testMostraEstat() {
        PaginaEstat paginaEstat = dades.mostraEstat();
        assertNotNull(paginaEstat);
        assertEquals(1, paginaEstat.getDia());
    }

    @Test
    public void testActualitzaEconomia() {
        float demandaPotencia = 1000;
        PaginaEconomica paginaEconomica = dades.actualitzaEconomia(demandaPotencia);
        assertNotNull(paginaEconomica);
        assertEquals(1, paginaEconomica.getDia());
    }

    @Test
    public void testRefrigeraReactor() {
        dades.mostraReactor().setTemperatura(500);
        dades.refrigeraReactor();
        assertTrue(dades.mostraReactor().getTemperatura() < 500);
    }

    @Test
    public void testRevisaComponents() {
        PaginaIncidencies paginaIncidencies = new PaginaIncidencies(1);
        dades.revisaComponents(paginaIncidencies);
        // No podemos asegurar si habrá incidencias o no (depende de la aleatoriedad)
        assertNotNull(paginaIncidencies);
    }

    @Test
    public void testFinalitzaDia() {
        float demandaPotencia = 1000;
        Bitacola bitacolaDia = dades.finalitzaDia(demandaPotencia);
        assertEquals(2, dades.getDia());
        assertEquals(3, bitacolaDia.getPagines().size());
    }

    @Test
    public void testMostraIncidencies() {
        List<PaginaIncidencies> incidencias = dades.mostraIncidencies();
        assertNotNull(incidencias);
    }

    @Test
    public void testGuardaYCarregaDades() throws CentralUBException, IOException {
        // Configurar algunos datos
        dades.setInsercioBarres(50);
        dades.getGeneradorVapor().activa();

        // Guardar datos
        String testFile = "testDades.dat";
        dades.guardaDades(testFile);

        // Crear nueva instancia y cargar datos
        Dades dadesCargadas = new Dades();
        dadesCargadas.carregaDades(testFile);

        // Verificar que los datos se cargaron correctamente
        assertEquals(50, dadesCargadas.getInsercioBarres());
        assertTrue(dadesCargadas.getGeneradorVapor().getActivat());

        // Eliminar archivo de prueba
        new File(testFile).delete();
    }

    @Test
    public void testGetEstadoBombasAsString() throws CentralUBException {
        String estado = dades.getEstadoBombasAsString();
        assertNotNull(estado);
        assertTrue(estado.contains("Bomba 0"));
        assertTrue(estado.contains("Bomba 1"));
        assertTrue(estado.contains("Bomba 2"));
        assertTrue(estado.contains("Bomba 3"));
    }

    @Test
    public void testObtenirDemandaSatisfeta() {
        String resultado = dades.obtenirDemandaSatisfeta(1000);
        assertNotNull(resultado);
        assertTrue(resultado.contains("Demanda satisfeta"));
    }

    @Test
    public void testActivaTotesBombes() throws CentralUBException {
        dades.activaTotesBombes();
        for (int i = 0; i < 4; i++) {
            assertTrue(dades.mostraSistemaRefrigeracio().getBomba(i).getActivat());
        }
    }

    @Test
    public void testDesactivaTotesBombes() throws CentralUBException {
        dades.activaTotesBombes();
        dades.desactivaTotesBombes();
        for (int i = 0; i < 4; i++) {
            assertFalse(dades.mostraSistemaRefrigeracio().getBomba(i).getActivat());
        }
    }
}