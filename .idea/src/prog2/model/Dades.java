package prog2.model;
import java.io.*;
import java.util.List;
import prog2.vista.CentralUBException;

public class Dades implements InDades, Serializable {
    public final static long  VAR_UNIF_SEED = 123;
    public final static float GUANYS_INICIALS = 0;
    public final static float PREU_UNITAT_POTENCIA = 1;
    public final static float PENALITZACIO_EXCES_POTENCIA = 250;

    // Afegir atributs:
    private VariableUniforme variableUniforme;
    private float insercioBarres;
    private Reactor reactor;
    private SistemaRefrigeracio sistemaRefrigeracio;
    private GeneradorVapor generadorVapor;
    private Turbina turbina;
    private Bitacola bitacola;
    private int dia;
    private float guanysAcumulats;

    public Dades(){
        // Inicialitza Atributs
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

        // Afegeix bombes refrigerants
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

    /**
     * Actualitza l'economia de la central. Genera una pàgina econòmica a
     * partir de la demanda de potencia actual. Aquesta pàgina econòmica inclou
     * beneficis, penalització per excès de potència, costos operatius y
     * guanys acumulats.
     * @param demandaPotencia Demanda de potència actual.
     */
    private PaginaEconomica actualitzaEconomia(float demandaPotencia) {
        // Completar
        float potenciaGenerada = calculaPotencia();
        float beneficis = Math.min(demandaPotencia, potenciaGenerada) * PREU_UNITAT_POTENCIA;
        float penalitzacio = (potenciaGenerada > demandaPotencia) ? PENALITZACIO_EXCES_POTENCIA : 0;

        float costOperatiu = 0;
        costOperatiu += reactor.getCostOperatiu();
        costOperatiu += sistemaRefrigeracio.getCostOperatiu();
        costOperatiu += generadorVapor.getCostOperatiu();
        costOperatiu += turbina.getCostOperatiu();

        guanysAcumulats += beneficis - penalitzacio - costOperatiu;

        PaginaEconomica paginaEconomica = new PaginaEconomica(dia, demandaPotencia, potenciaGenerada,
                (demandaPotencia > 0) ? (beneficis / demandaPotencia) * 100 : 0,
                beneficis,penalitzacio,costOperatiu,guanysAcumulats);
        return paginaEconomica;
    }

    /**
     * Aquest mètode ha de establir la nova temperatura del reactor.
     */
    private void refrigeraReactor() {
        // Completar
        float temperaturaInicial = reactor.getTemperatura();
        float temperaturaRefrigerada = sistemaRefrigeracio.calculaOutput(temperaturaInicial);
        float temperaturaFinal = Math.max(25, temperaturaInicial - temperaturaRefrigerada);
        reactor.setTemperatura(temperaturaFinal);
    }

    /**
     * Aquest mètode ha de revisar els components de la central. Si
     * es troben incidències, s'han de registrar en la pàgina d'incidències
     * que es proporciona com a paràmetre d'entrada.
     * @param paginaIncidencies Pàgina d'incidències.
     */
    private void revisaComponents(PaginaIncidencies paginaIncidencies) {
        // Completar
        reactor.revisa(paginaIncidencies);
        sistemaRefrigeracio.revisa(paginaIncidencies);
        generadorVapor.revisa(paginaIncidencies);
        turbina.revisa(paginaIncidencies);
    }

    /**
     * Duu a terme les accions relacionades amb la finalització d'un dia.
     * Per això és necessari coneixer la demanda de potència actual per al dia
     * en curs.
     * @param demandaPotencia Demanda de potència actual de la central.
     */
    public Bitacola finalitzaDia(float demandaPotencia) {
        // Actualitza economia
        PaginaEconomica paginaEconomica = actualitzaEconomia(demandaPotencia);

        // Genera pàgina d'estat amb la configuració escollida (la nova pàgina
        // d'estat inclou la nova configuració escollida pel operador abans de
        // refrigerar el reactor)
        PaginaEstat paginaEstat = mostraEstat();

        // Actualitza estat de la central...

        // Refrigera el reactor
        refrigeraReactor();

        // Revisa els components de la central i registra incidències
        PaginaIncidencies paginaIncidencies = new PaginaIncidencies(dia);
        revisaComponents(paginaIncidencies);

        // Incrementa dia
        dia += 1;

        // Guarda pàgines de bitacola
        bitacola.afegeixPagina(paginaEconomica);
        bitacola.afegeixPagina(paginaEstat);
        bitacola.afegeixPagina(paginaIncidencies);

        // Retorna pàgines
        Bitacola bitacolaDia = new Bitacola();
        bitacolaDia.afegeixPagina(paginaEconomica);
        bitacolaDia.afegeixPagina(paginaEstat);
        bitacolaDia.afegeixPagina(paginaIncidencies);
        return bitacolaDia;
    }

    /**
     * Retorna el número del dia actual en la simulació.
     *
     * @return El número del dia actual (enter).
     */
    public int getDia() {
        return dia;
    }

    /**
     * Retorna l'objecte GeneradorVapor associat a la central.
     *
     * @return L'objecte GeneradorVapor.
     */
    public GeneradorVapor getGeneradorVapor() {
        return generadorVapor;
    }

    /**
     * Retorna l'objecte Turbina associat a la central.
     *
     * @return L'objecte Turbina.
     */
    public Turbina getTurbina() {
        return turbina;
    }
    /**
     * Retorna el grau d'inserció de les barres de control en percentatge.
     *
     * @return El grau d'inserció de les barres.
     */
    @Override
    public float getInsercioBarres() {
        return insercioBarres;
    }

    /**
     * Estableix el grau d'inserció de les barres de control.
     *
     * @param insercioBarres El nou grau d'inserció de les barres (0-100).
     * @throws CentralUBException Si el grau dinserció és fora del rang vàlid.
     */
    @Override
    public void setInsercioBarres(float insercioBarres) throws CentralUBException {
        if (insercioBarres < 0 || insercioBarres > 100) {
            throw new CentralUBException("Error: El grau d'inserció de les barres ha d'estar entre 0 i 100.");
        }
        this.insercioBarres = (int) insercioBarres;
    }

    /**
     * Estableix el grau d'inserció de les barres de control en percentatge.
     *
     * @throws CentralUBException Si el grau d'inserció és fora del rang vàlid.
     */
    @Override
    public void activaReactor() throws CentralUBException {
        if (reactor.getTemperatura() > 1000) {
            throw new CentralUBException("Error: No es pot activar el reactor mentre la temperatura superi els 1000 graus.");
        }
        reactor.activa();
    }

    /**
     * Desactiva el reactor de la central.
     */
    @Override
    public void desactivaReactor() {
        reactor.desactiva();
    }

    /**
     * Retorna l'objecte que contè el reactor de la central.
     *
     *  @return El objete Reactor.
     */
    public Reactor mostraReactor() {
        return reactor;
    }

    /**
     * Activa una bomba refrigerant amb Id donat com a paràmetre.
     * @param id Identificador de la bomba que es vol activar.
     * @throws CentralUBException Si la bomba està fora de servei.
     */
    public void activaBomba(int id) throws CentralUBException {
        BombaRefrigerant bomba = sistemaRefrigeracio.getBomba(id);
        if (bomba.getForaDeServei()) {
            throw new CentralUBException("No es pot activar una bomba refrigerant que està fora de servei.");
        }
        bomba.activa();
    }


    /**
     * Desactiva una bomba refrigerant amb Id donat com a paràmetre.
     * @param id Identificador de la bomba que es vol activar.
     */
    public void desactivaBomba(int id) {
        try {
            BombaRefrigerant bomba = sistemaRefrigeracio.getBomba(id);
            if (bomba != null) {
                bomba.desactiva();
            }
            // Si no troba la bomba, no fem res (o podríem registrar un log)
        } catch (Exception e) {
            // No fem res o podríem registrar l'error en un log
            System.err.println("No s'ha pogut desactivar la bomba " + id + ": " + e.getMessage());
        }
    }

    /**
     * Retorna l'objecte que contè el sistema de refrigeració de la central.
     */
    public SistemaRefrigeracio mostraSistemaRefrigeracio(){
        return sistemaRefrigeracio;
    }

    /**
     * Retorna la potència generada per la central. Aquesta potència es
     * l'output de la turbina. Es pot consultar la Figura 2 a l'enunciat per
     * veure els detalls.
     */
    public float calculaPotencia(){
        float outputReactor = reactor.calculaOutput(insercioBarres);
        float outputSistemaRefrigeracio = sistemaRefrigeracio.calculaOutput(outputReactor);
        float outputGeneradorVapor = generadorVapor.calculaOutput(outputSistemaRefrigeracio);
        return turbina.calculaOutput(outputGeneradorVapor);
    }

    /**
     * Retorna els guanys acumulats actuals.
     */
    public float getGuanysAcumulats(){
        return guanysAcumulats;
    }

    /**
     * Retorna una pàgina d'estat per a la configuració actual de la central.
     */
    public PaginaEstat mostraEstat(){
        float insercioBarres = this.insercioBarres;
        float outputReactor = reactor.calculaOutput(insercioBarres);
        float outputSistemaRefrigeracio = sistemaRefrigeracio.calculaOutput(outputReactor);
        float outputGeneradorVapor = generadorVapor.calculaOutput(outputSistemaRefrigeracio);
        float outputGeneradorTurbina = turbina.calculaOutput(outputGeneradorVapor);

        return new PaginaEstat(dia, insercioBarres, outputReactor, outputSistemaRefrigeracio, outputGeneradorVapor, outputGeneradorTurbina);
    }


    /**
     * Retorna la bitacola de la central.
     */
    public Bitacola mostraBitacola(){
        return bitacola;
    }

    /**
     * Retorna una llista amb totes les pàgines d'incidències de la bitàcola de
     * la central.
     */
    public List<PaginaIncidencies> mostraIncidencies(){
        return bitacola.getIncidencies();
    }


    public void guardaDades(String camiDesti) throws CentralUBException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(camiDesti))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new CentralUBException("Error al guardar les dades: " + e.getMessage());
        }
    }

    /**
     * Retorna la temperatura actual del reactor.
     *
     * @return La temperatura del reactor en grados Celsius.
     */
    public float getReactorTemperatura() {
        return reactor.getTemperatura();
    }


    public void carregaDades(String camiOrigen) throws CentralUBException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(camiOrigen))) {
            Dades loadedDades = (Dades) ois.readObject();
            this.variableUniforme = loadedDades.variableUniforme;
            this.insercioBarres = loadedDades.insercioBarres;
            this.reactor = loadedDades.reactor;
            this.sistemaRefrigeracio = loadedDades.sistemaRefrigeracio;
            this.generadorVapor = loadedDades.generadorVapor;
            this.turbina = loadedDades.turbina;
            this.bitacola = loadedDades.bitacola;
            this.dia = loadedDades.dia;
            this.guanysAcumulats = loadedDades.guanysAcumulats;
        } catch (IOException | ClassNotFoundException e) {
            throw new CentralUBException("Error al carregar les dades: " + e.getMessage());
        }
    }

    /**
     * Retorna el estado de todas las bombas del sistema de refrigeración
     * como una cadena de texto.
     *
     * @return Cadena con el estado de las bombas.
     * @throws CentralUBException Si ocurre un error al obtener el estado.
     */
    public String getEstadoBombasAsString() throws CentralUBException {
        SistemaRefrigeracio sistemaRefrigeracio = this.mostraSistemaRefrigeracio();
        StringBuilder estatBombes = new StringBuilder("Estat de les bombes:\n");
        for (int i = 0; i < 4; i++) {
            try {
                BombaRefrigerant bomba = sistemaRefrigeracio.getBomba(i);
                estatBombes.append("Bomba ").append(bomba.getId()).append(": ");
                estatBombes.append(bomba.getActivat() ? "Activa" : "Desactivada");
                estatBombes.append(bomba.getForaDeServei() ? " (Fora de servei)" : "").append("\n");
            } catch (CentralUBException e) {
                estatBombes.append("Bomba ").append(i).append(": No disponible\n");
            }
        }
        return estatBombes.toString();
    }

    /**
     * Calcula y retorna una cadena con el porcentaje de demanda de potencia satisfecha.
     *
     * @param demandaPotencia La demanda de potencia.
     * @return Una cadena con el porcentaje de demanda satisfecha.
     */
    public String obtenirDemandaSatisfeta(float demandaPotencia) {
        float potenciaGenerada = calculaPotencia(); // Asumo que calculaPotencia() está definido en Dades
        float percentatge = 0;
        if (demandaPotencia > 0) {
            percentatge = (potenciaGenerada / demandaPotencia) * 100;
        }
        return "Demanda satisfeta: " + String.format("%.2f", percentatge) + " %";
    }
    /**
     *
     * Activa totes les bombes del sistema de refrigeració.
     * @throws CentralUBException
     */
    public void activaTotesBombes() throws CentralUBException {
        for (int i = 0; i < 4; i++) {
            activaBomba(i);
        }
    }

    /**
     * Desactiva todas las bombas del sistema de refrigeración.
     */
    public void desactivaTotesBombes() {
        SistemaRefrigeracio sistemaRefrigeracio = this.mostraSistemaRefrigeracio();
        sistemaRefrigeracio.desactiva();
    }
}