package prog2.model;

import java.util.ArrayList;
import java.util.List;

import prog2.vista.CentralUBException;

public class SistemaRefrigeracio implements InComponent {

    private ArrayList<BombaRefrigerant> bombes;

    public SistemaRefrigeracio() {
        this.bombes = new ArrayList<>();
    }

    public void afegirBomba(BombaRefrigerant bomba) {
        bombes.add(bomba);
    }

    @Override
    public void activa() throws CentralUBException {
        for (BombaRefrigerant bomba : bombes) {
            if (!bomba.getForaDeServei()) {
                bomba.activa();
            }
        }
    }

    @Override
    public void desactiva() {
        for (BombaRefrigerant bomba : bombes) {
            bomba.desactiva();
        }
    }

    @Override
    public boolean getActivat() {
        for (BombaRefrigerant bomba : bombes) {
            if (bomba.getActivat()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void revisa(PaginaIncidencies p) {
        for (BombaRefrigerant bomba : bombes) {
            bomba.revisa(p);
            if (bomba.getForaDeServei() && bomba.getActivat()) {
                bomba.desactiva();
            }
        }
    }

    @Override
    public float getCostOperatiu() {
        float cost = 0;
        for (BombaRefrigerant bomba : bombes) {
            if (bomba.getActivat()) {
                cost = cost + bomba.getCostOperatiu();
            }
        }
        return cost;
    }

    @Override
    public float calculaOutput(float input) {
        int bombesActives = 0;
        for (BombaRefrigerant bomba : bombes) {
            if (bomba.getActivat()) {
                bombesActives++;
            }
        }
        float capacitatTotal = 250 * bombesActives;

        return Math.min(input, capacitatTotal);
    }

    public BombaRefrigerant getBomba(int id) throws CentralUBException {
        for (BombaRefrigerant bomba : bombes) {
            if (bomba.getId() == id) {
                return bomba;
            }
        }
        throw new CentralUBException("No s'ha trobat la bomba amb ID: " + id);
    }
}
