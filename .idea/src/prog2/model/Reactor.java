package prog2.model;

import prog2.vista.CentralUBException;


public class Reactor implements InComponent {
    private boolean activat;
    private float temperatura;
    private float insercioBarres; // valor entre 0 i 100
    private boolean foraDeServei;


    public Reactor() {
        this.activat = false;
        this.temperatura = 25; // Temperatura ambient inicial
        this.insercioBarres = 100; // inserides per defecte (reactor aturat)
        this.foraDeServei = false;
    }

    /**
     * Activa el reactor si no està fora de servei i la inserció de barres és prou alta.
     */
    @Override
    public void activa() throws CentralUBException {
        if (foraDeServei) {
            throw new CentralUBException("Reactor fora de servei");
        }
        if (temperatura > 1000) {
            throw new CentralUBException("No es pot activar el reactor amb temperatura > 1000°C");
        }
        if (insercioBarres < 20) {
            throw new CentralUBException("Inserció de barres massa baixa per activar (<20%)");
        }
        this.activat = true;
    }

    /**
     * Desactiva el reactor i restableix la temperatura.
     */
    @Override
    public void desactiva() {
        this.activat = false;
        this.temperatura = 25;
    }

    /**
     * Retorna si el reactor està activat.
     */
    @Override
    public boolean getActivat() {
        return activat;
    }

    /**
     * Calcula l'output en funció de l'input i la inserció de barres.
     */
    @Override
    public float calculaOutput(float input) {
        if (!activat || foraDeServei) return temperatura;
        return (100 - insercioBarres) * input / 100;
    }

    /**
     * Revisa si hi ha incidències relacionades amb temperatura o inserció de barres.
     */
    @Override
    public void revisa(PaginaIncidencies p) {
        if (temperatura > 1000) {
            p.afegeixIncidencia("Reactor: Temperatura crítica (" + temperatura + "°C)");
            foraDeServei = true;
            desactiva();
        }
        if (insercioBarres < 5 && activat) {
            p.afegeixIncidencia("Reactor: Inserció de barres massa baixa (" + insercioBarres + "%)");
        }
    }

    /**
     * Retorna el cost operatiu del reactor si està activat i no fora de servei.
     */
    @Override
    public float getCostOperatiu() {
        return (activat && !foraDeServei) ? 35f : 0f;
    }

    /**
     * Getter de la temperatura actual del reactor.
     */
    public float getTemperatura() {
        return temperatura;
    }

    /**
     * Setter per la temperatura del reactor.
     */
    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * Getter per la inserció de barres.
     */
    public float getInsercioBarres() {
        return insercioBarres;
    }

    /**
     * Setter per la inserció de barres.
     */
    public void setInsercioBarres(float insercioBarres) {
        this.insercioBarres = insercioBarres;
    }

    @Override
    public String toString() {
        return "Reactor [activat=" + activat +
                ", temperatura=" + temperatura +
                "°C, insercioBarres=" + insercioBarres +
                "%, foraDeServei=" + foraDeServei + "]";
    }

}
