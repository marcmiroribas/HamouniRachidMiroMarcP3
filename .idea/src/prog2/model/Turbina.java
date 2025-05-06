package prog2.model;

import prog2.vista.CentralUBException;

public class Turbina implements InComponent {

    private boolean activat;

    public Turbina() {
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
        // No hi ha incidències per a la turbina
    }

    @Override
    public float getCostOperatiu() {
        return activat ? 20f : 0f;
    }

    @Override
    public float calculaOutput(float input) {
        return (activat && input >= 100) ? input * 2f : 0f;
    }
}
