package prog2.model;

import prog2.vista.CentralUBException;

public class GeneradorVapor implements InComponent {

    private boolean activat;

    public GeneradorVapor() {
        this.activat = false;
    }

    @Override
    public void activa() throws CentralUBException {
        this.activat = true;
    }

    @Override
    public void desactiva() {
        this.activat = false;
    }

    @Override
    public boolean getActivat() {
        return this.activat;
    }

    @Override
    public void revisa(PaginaIncidencies p) {
        // No hi ha incidències per al generador de vapor
    }

    @Override
    public float getCostOperatiu() {
        return activat ? 25f : 0f;
    }

    @Override
    public float calculaOutput(float input) {
        return activat ? input * 0.9f : 0f;
    }
}
