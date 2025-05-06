package prog2.model;

import java.util.ArrayList;
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
            if (!bomba.estaForaServei()) {
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
            if (bomba.getActivada()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void revisa(PaginaIncidencies p) {
        for (BombaRefrigerant bomba : bombes) {
            bomba.revisa(p);
            if (bomba.estaForaServei() && bomba.getActivat()) {
                bomba.desactiva();
            }
        }
    }

    @Override
    public float getCostOperatiu() {
        float cost = 0;
        for (BombaRefrigerant bomba : bombes) {
            if (bomba.getActivada()) {
                cost = cost + bomba.getCostOperatiu();
            }
        }
        return cost;
    }

    @Override
    public float calculaOutput(float input) {
        int bombesActives = 0;
        for (BombaRefrigerant bomba : bombes) {
            if (bomba.getActivada()) {
                bombesActives++;
            }
        }
        float capacitatTotal = 250 * bombesActives;

        return Math.min(input, capacitatTotal);
    }
}
