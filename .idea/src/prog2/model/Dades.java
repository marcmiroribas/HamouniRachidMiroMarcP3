/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog2.model;

import java.util.ArrayList;
import java.util.List;
import prog2.vista.CentralUBException;

/**
 *
 * @author Daniel Ortiz
 */
public class Dades implements InDades {
    public final static long VAR_UNIF_SEED = 123;
    public final static float GUANYS_INICIALS = 0;
    public final static float PREU_UNITAT_POTENCIA = 1;
    public final static float PENALITZACIO_EXCES_POTENCIA = 250;

    private float insercioBarres;
    private Reactor reactor;
    private SistemaRefrigeracio sistemaRefrigeracio;
    private GeneradorVapor generadorVapor;
    private Turbina turbina;
    private VariableUniforme variableUniforme;
    private Bitacola bitacola;
    private int dia;
    private float guanysAcumulats;

    public Dades() {
        this.variableUniforme = new VariableUniforme(VAR_UNIF_SEED);
        this.insercioBarres = 100;
        this.reactor = new Reactor();
        this.reactor.desactiva();
        this.sistemaRefrigeracio = new SistemaRefrigeracio();
        this.generadorVapor = new GeneradorVapor();
        this.generadorVapor.activa();
        this.turbina = new Turbina();
        this.turbina.activa();
        this.bitacola = new Bitacola();
        this.dia = 1;
        this.guanysAcumulats = GUANYS_INICIALS;

        BombaRefrigerant b0 = new BombaRefrigerant(variableUniforme, 0);
        BombaRefrigerant b1 = new BombaRefrigerant(variableUniforme, 1);
        BombaRefrigerant b2 = new BombaRefrigerant(variableUniforme, 2);
        BombaRefrigerant b3 = new BombaRefrigerant(variableUniforme, 3);

        this.sistemaRefrigeracio.afegirBomba(b0);
        this.sistemaRefrigeracio.afegirBomba(b1);
        this.sistemaRefrigeracio.afegirBomba(b2);
        this.sistemaRefrigeracio.afegirBomba(b3);

        this.sistemaRefrigeracio.desactiva();
    }

    @Override
    public float getInsercioBarres() {
        return insercioBarres;
    }

    @Override
    public void setInsercioBarres(float insercioBarres) throws CentralUBException {
        if (insercioBarres < 0 || insercioBarres > 100) {
            throw new CentralUBException("Inserció fora del rang [0,100]");
        }
        this.insercioBarres = insercioBarres;
    }

    @Override
    public void activaReactor() throws CentralUBException {
        if (reactor.getTemperatura() > 1000) {
            throw new CentralUBException("No es pot activar el reactor amb temperatura > 1000 graus");
        }
        reactor.activa();
    }

    @Override
    public void desactivaReactor() {
        reactor.desactiva();
    }

    @Override
    public Reactor mostraReactor() {
        return reactor;
    }

    @Override
    public void activaBomba(int id) throws CentralUBException {
        sistemaRefrigeracio.activaBomba(id);
    }

    @Override
    public void desactivaBomba(int id) {
        sistemaRefrigeracio.desactivaBomba(id);
    }

    @Override
    public SistemaRefrigeracio mostraSistemaRefrigeracio() {
        return sistemaRefrigeracio;
    }

    @Override
    public float calculaPotencia() {
        float temp = reactor.calculaOutput(insercioBarres);
        temp = sistemaRefrigeracio.calculaOutput(temp);
        temp = generadorVapor.calculaOutput(temp);
        return turbina.calculaOutput(temp);
    }

    @Override
    public float getGuanysAcumulats() {
        return guanysAcumulats;
    }

    @Override
    public PaginaEstat mostraEstat() {
        return new PaginaEstat(insercioBarres, reactor.getTemperatura(),
                sistemaRefrigeracio.getBombes(), calculaPotencia(), dia);
    }

    @Override
    public Bitacola mostraBitacola() {
        return bitacola;
    }

    @Override
    public List<PaginaIncidencies> mostraIncidencies() {
        return bitacola.getPaginesIncidencies();
    }

    /**
     * Actualitza l'economia de la central. Genera una pàgina econòmica a
     * partir de la demanda de potencia actual. Aquesta pàgina econòmica inclou
     * beneficis, penalització per excès de potència, costos operatius y
     * guanys acumulats.
     * @param demandaPotencia Demanda de potència actual.
     */
    private PaginaEconomica actualitzaEconomia(float demandaPotencia) {
        float potGenerada = calculaPotencia();
        float beneficis = Math.min(potGenerada, demandaPotencia) * PREU_UNITAT_POTENCIA;
        float penalitzacio = (potGenerada > demandaPotencia) ? PENALITZACIO_EXCES_POTENCIA : 0;
        float costos = reactor.getCostOperatiu() + sistemaRefrigeracio.getCostOperatiu() +
                generadorVapor.getCostOperatiu() + turbina.getCostOperatiu();
        float guany = beneficis - penalitzacio - costos;
        guanysAcumulats += guany;
        return new PaginaEconomica(dia, beneficis, penalitzacio, costos, guany, guanysAcumulats);
    }

    /**
     * Aquest mètode ha de establir la nova temperatura del reactor.
     */
    private void refrigeraReactor() {
        float novaTemp = sistemaRefrigeracio.calculaOutput(reactor.getTemperatura());
        reactor.setTemperatura(novaTemp);

        if (reactor.getTemperatura() > 1000) {
            reactor.desactiva();
        }
    }

    /**
     * Aquest mètode ha de revisar els components de la central. Si
     * es troben incidències, s'han de registrar en la pàgina d'incidències
     * que es proporciona com a paràmetre d'entrada.
     * @param paginaIncidencies Pàgina d'incidències.
     */
    private void revisaComponents(PaginaIncidencies paginaIncidencies) {
        reactor.revisa(paginaIncidencies);
        sistemaRefrigeracio.revisa(paginaIncidencies);
        generadorVapor.revisa(paginaIncidencies);
        turbina.revisa(paginaIncidencies);
    }

    @Override
    public Bitacola finalitzaDia(float demandaPotencia) {
        PaginaEconomica paginaEconomica = actualitzaEconomia(demandaPotencia);
        PaginaEstat paginaEstat = mostraEstat();
        refrigeraReactor();
        PaginaIncidencies paginaIncidencies = new PaginaIncidencies(dia);
        revisaComponents(paginaIncidencies);
        dia++;
        bitacola.afegeixPagina(paginaEconomica);
        bitacola.afegeixPagina(paginaEstat);
        bitacola.afegeixPagina(paginaIncidencies);

        Bitacola bitacolaDia = new Bitacola();
        bitacolaDia.afegeixPagina(paginaEconomica);
        bitacolaDia.afegeixPagina(paginaEstat);
        bitacolaDia.afegeixPagina(paginaIncidencies);
        return bitacolaDia;
    }
}
