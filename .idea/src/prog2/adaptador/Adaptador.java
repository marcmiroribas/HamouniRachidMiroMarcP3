package prog2.adaptador;

import prog2.model.*;
import prog2.vista.CentralUBException;
import java.io.*;
import java.util.List;

public class Adaptador implements Serializable {
    private Dades dades;

    public Adaptador() {
        this.dades = new Dades();
    }

    // Métodos para barras de control
    public void setInsercioBarres(float percentatge) throws CentralUBException {
        dades.setInsercioBarres(percentatge);
    }

    public float getInsercioBarres() {
        return dades.getInsercioBarres();
    }

    // Métodos para el reactor
    public void activaReactor() throws CentralUBException {
        dades.activaReactor();
    }

    public void desactivaReactor() {
        dades.desactivaReactor();
    }

    public boolean isReactorActivat() {
        return dades.mostraReactor().getActivat();
    }

    public float getTemperaturaReactor() {
        return dades.getReactorTemperatura();
    }

    // Métodos para el sistema de refrigeración
    public void activaBomba(int id) throws CentralUBException {
        dades.activaBomba(id);
    }

    public void desactivaBomba(int id) throws CentralUBException{
        dades.desactivaBomba(id);
    }

    public void activaTotesBombes() throws CentralUBException {
        dades.activaTotesBombes();
    }

    public void desactivaTotesBombes() {
        dades.desactivaTotesBombes();
    }

    public String getEstatBombes() throws CentralUBException {
        return dades.getEstadoBombasAsString();
    }

    // Métodos para mostrar información
    public String getEstadoCentralAsString() {
        return dades.mostraEstat().toString();
    }

    public String getDemandaSatisfeta(float demandaPotencia) {
        return dades.obtenirDemandaSatisfeta(demandaPotencia);
    }

    public String getBitacolaAsString() {
        return dades.mostraBitacola().toString();
    }

    public String getIncidenciasAsString() {
        StringBuilder sb = new StringBuilder();
        List<PaginaIncidencies> incidencias = dades.mostraIncidencies();
        for (PaginaIncidencies pagina : incidencias) {
            sb.append(pagina.toString()).append("\n");
        }
        return sb.toString();
    }

    // Métodos para gestión del tiempo
    public String finalitzaDia(float demandaPotencia) {
        Bitacola bitacola = dades.finalitzaDia(demandaPotencia);
        return bitacola.toString();
    }

    // Métodos para persistencia
    public void guardaDades(String cami) throws CentralUBException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cami))) {
            oos.writeObject(dades);
        } catch (IOException e) {
            throw new CentralUBException("Error al guardar les dades: " + e.getMessage());
        }
    }

    public void carregaDades(String cami) throws CentralUBException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cami))) {
            this.dades = (Dades) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new CentralUBException("Error al carregar les dades: " + e.getMessage());
        }
    }

    // Métodos adicionales para la vista
    public float getPotenciaGenerada() {
        return dades.calculaPotencia();
    }

    public float getGuanysAcumulats() {
        return dades.getGuanysAcumulats();
    }

    public int getDiaActual() {
        return dades.getDia();
    }
}