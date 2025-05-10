/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog2.vista;
import prog2.adaptador.Adaptador;

import java.util.Scanner;

/**
 *
 * @author Daniel Ortiz
 */
public class CentralUB {
    public final static float DEMANDA_MAX = 1800;
    public final static float DEMANDA_MIN = 250;
    public final static float VAR_NORM_MEAN = 1000;
    public final static float VAR_NORM_STD = 800;
    public final static long VAR_NORM_SEED = 123;

    /* Generador aleatori de la demanda de potència */
    private VariableNormal variableNormal;

    /* Demanda de potència del dia actual */
    private float demandaPotencia;

    /**
     * Adaptador per interactuar amb el model
     */
    private Adaptador adaptador;

    /* Scanner per llegir l'entrada de l'usuari */
    private Scanner sc;

    /* Menú d'opcions */
    private Menu<OpcionsMenu> menuPrincipal;


    /* Constructor*/
    public CentralUB() {
        variableNormal = new VariableNormal(VAR_NORM_MEAN, VAR_NORM_STD, VAR_NORM_SEED);
        demandaPotencia = generaDemandaPotencia();

        // Afegir codi adicional si fos necessari:
        adaptador = new Adaptador();
        sc = new Scanner(System.in);
        menuPrincipal = new Menu<>("Gestió Central UB", OpcionsMenu.values());
        menuPrincipal.setDescripcions(new String[]{
                "Gestió Barres de Control",
                "Gestió Reactor",
                "Gestió Sistema Refrigeració",
                "Mostrar estat de la central",
                "Mostrar bitàcola",
                "Mostrar incidències",
                "Mostrar demanda satisfeta",
                "Finalitzar dia",
                "Guardar dades",
                "Carregar dades",
                "Sortir de l'aplicació"
        });
    }

    public void gestioCentralUB() {
        // Mostrar missatge inicial
        System.out.println("Benvingut a la planta PWR de la UB");
        System.out.println("La demanda de potència elèctrica avui es de " + demandaPotencia + " unitats");

        // Completar
        OpcionsMenu opcioPrincipal;
        do {
            menuPrincipal.mostrarMenu();
            opcioPrincipal = menuPrincipal.getOpcio(sc);
            try {
                switch (opcioPrincipal) {
                    case GESTIO_BARRES_CONTROL:
                        gestioBarresControlSubmenu();
                        break;
                    case GESTIO_REACTOR:
                        gestioReactorSubmenu();
                        break;
                    case GESTIO_SISTEMA_REFRIGERACIO:
                        gestioSistemaRefrigeracioSubmenu();
                        break;
                    case MOSTRAR_ESTAT_CENTRAL:
                        mostrarEstatCentral();
                        break;
                    case MOSTRAR_BITACOLA:
                        mostrarBitacola();
                        break;
                    case MOSTRAR_INCIDENCIES:
                        mostrarIncidencies();
                        break;
                    case MOSTRAR_DEMANDA_SATISFETA:
                        mostrarDemandaSatisfeta();
                        break;
                    case FINALITZAR_DIA:
                        finalitzaDia();
                        break;
                    case GUARDAR_DADES:
                        guardarDades();
                        break;
                    case CARREGAR_DADES:
                        carregarDades();
                        break;
                    case SORTIR:
                        break;
                    default:
                        System.out.println("Opció no vàlida.");
                }
            } catch (CentralUBException e) {
                System.err.println("Error: " + e.getMessage());
            }
            System.out.println();
        } while (opcioPrincipal != OpcionsMenu.SORTIR);

        System.out.println("Gràcies per utilitzar la gestió de la central UB.");
        sc.close();
    }

    private void gestioBarresControlSubmenu () {
        Menu<OpcionsBarresControl> menuBarres = new Menu<>("Gestió Barres de Control", OpcionsBarresControl.values());
        menuBarres.setDescripcions(new String[]{"Mostrar inserció", "Establir inserció", "Tornar al menú principal"});
        OpcionsBarresControl opcioBarres;
        do {
            menuBarres.mostrarMenu();
            opcioBarres = menuBarres.getOpcio(sc);
            switch (opcioBarres) {
                case MOSTRAR_INSERCIO:
                    mostrarEstatCentral();
                    break;
                case ESTABLIR_INSERCIO:
                    try {
                        establirInsercioBarres();
                    } catch (CentralUBException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case SORTIR:
                    break;
            }
        } while (opcioBarres != OpcionsBarresControl.SORTIR);
    }

    private void gestioReactorSubmenu() throws CentralUBException {
        Menu<OpcionsReactor> menuReactor = new Menu<>("Gestió Reactor", OpcionsReactor.values());
        menuReactor.setDescripcions(new String[]{"Activar reactor", "Desactivar reactor", "Mostrar estat", "Tornar al menú principal"});
        OpcionsReactor opcioReactor;
        do {
            menuReactor.mostrarMenu();
            opcioReactor = menuReactor.getOpcio(sc);
            switch (opcioReactor) {
                case ACTIVAR:
                    activarReactor();
                    break;
                case DESACTIVAR:
                    desactivarReactor();
                    break;
                case MOSTRAR_ESTAT:
                    mostrarEstatReactor();
                    break;
                case SORTIR:
                    break;
            }
        } while (opcioReactor != OpcionsReactor.SORTIR);
    }

    private void gestioSistemaRefrigeracioSubmenu() throws CentralUBException {
        Menu<OpcionsSistemaRefrigeracio> menuRef = new Menu<>("Gestió Sistema Refrigeració", OpcionsSistemaRefrigeracio.values());
        menuRef.setDescripcions(new String[]{"Activar totes les bombes", "Desactivar totes les bombes", "Activar bomba", "Desactivar bomba", "Mostrar estat", "Tornar al menú principal"});
        OpcionsSistemaRefrigeracio opcioRef;
        do {
            menuRef.mostrarMenu();
            opcioRef = menuRef.getOpcio(sc);
            switch (opcioRef) {
                case ACTIVAR_TOTES:
                    activarTotesBombes();
                    break;
                case DESACTIVAR_TOTES:
                    desactivarTotesBombes();
                    break;
                case ACTIVAR_BOMBA:
                    activarBomba();
                    break;
                case DESACTIVAR_BOMBA:
                    desactivarBomba();
                    break;
                case MOSTRAR_ESTAT:
                    mostrarEstatBombes();
                    break;
                case SORTIR:
                    break;
            }
        } while (opcioRef != OpcionsSistemaRefrigeracio.SORTIR);
    }

    private void establirInsercioBarres() throws CentralUBException {
        System.out.print("Entra el grau d'inserció de les barres (0-100): ");
        float insercio = sc.nextFloat();
        sc.nextLine();
        adaptador.setInsercioBarres(insercio);
        System.out.println("Grau d'inserció de barres establert a " + insercio + "%.");
    }

    private void activarReactor() throws CentralUBException {
        adaptador.activaReactor();
        System.out.println("Reactor activat.");
    }

    private void desactivarReactor() {
        adaptador.desactivaReactor();
        System.out.println("Reactor desactivat.");
    }

    private void mostrarEstatReactor() {
        boolean activat = adaptador.isReactorActivat();
        float temperatura = adaptador.getTemperaturaReactor();
        System.out.println("Estat del Reactor: " + (activat ? "Activat" : "Desactivat"));
        System.out.println("Temperatura del Reactor: " + String.format("%.2f", temperatura) + " ºC");
    }

    private void activarBomba() throws CentralUBException {
        System.out.print("Entra l'ID de la bomba a activar (0-3): ");
        int idBomba = sc.nextInt();
        sc.nextLine();
        adaptador.activaBomba(idBomba);
        System.out.println("Bomba " + idBomba + " activada.");
    }

    private void desactivarBomba() {
        System.out.print("Entra l'ID de la bomba a desactivar (0-3): ");
        int idBomba = sc.nextInt();
        sc.nextLine();
        try {
            adaptador.desactivaBomba(idBomba);
            System.out.println("Bomba " + idBomba + " desactivada.");
        } catch (CentralUBException e) {
            System.err.println("Error al desactivar bomba: " + e.getMessage());
        }
    }

    private void activarTotesBombes() throws CentralUBException {
        adaptador.activaTotesBombes();
        System.out.println("Totes les bombes activades.");
    }

    private void desactivarTotesBombes() {
        adaptador.desactivaTotesBombes();
        System.out.println("Totes les bombes desactivades.");
    }

    private void mostrarEstatCentral() {
        System.out.println("\n--- Estat de la Central ---");
        System.out.println(adaptador.getEstadoCentralAsString());
        System.out.println("---------------------------\n");
    }

    private void mostrarEstatBombes() throws CentralUBException {
        System.out.println(adaptador.getEstatBombes());
    }

    private void mostrarBitacola() {
        System.out.println("\n--- Bitàcola ---");
        System.out.println(adaptador.getBitacolaAsString());
        System.out.println("---------------\n");
    }

    private void mostrarIncidencies() {
        System.out.println("\n--- Incidències ---");
        System.out.println(adaptador.getIncidenciasAsString());
        System.out.println("-------------------\n");
    }

    private void mostrarDemandaSatisfeta() {
        System.out.println(adaptador.getDemandaSatisfeta(demandaPotencia));
    }

    private void finalitzaDia() {
        // Finalitzar dia i imprimir informacio de la central
        String info = this.adaptador.finalitzaDia(demandaPotencia);
        System.out.println(info);
        System.out.println("Dia finalitzat\n");

        // Generar i mostrar nova demanda de potencia
        demandaPotencia = generaDemandaPotencia();
        System.out.println("La demanda de potència elèctrica avui es de " + demandaPotencia + " unitats");
    }

    private void guardarDades() {
        System.out.print("Entra el nom del fitxer per guardar les dades: ");
        String nomFitxer = sc.nextLine();
        try {
            adaptador.guardaDades(nomFitxer);
            System.out.println("Dades guardades correctament a " + nomFitxer);
        } catch (CentralUBException e) {
            System.err.println("Error al guardar les dades: " + e.getMessage());
        }
    }

    private void carregarDades() {
        System.out.print("Entra el nom del fitxer per carregar les dades: ");
        String nomFitxer = sc.nextLine();
        try {
            adaptador.carregaDades(nomFitxer);
            System.out.println("Dades carregades correctament des de " + nomFitxer);
            demandaPotencia = generaDemandaPotencia();
            System.out.println("La demanda de potència elèctrica per avui és de " + demandaPotencia + " unitats");
        } catch (CentralUBException e) {
            System.err.println("Error al carregar les dades: " + e.getMessage());
        }
    }

    private float generaDemandaPotencia() {
        float valor = Math.round(variableNormal.seguentValor());
        if (valor > DEMANDA_MAX) return DEMANDA_MAX;
        else if (valor < DEMANDA_MIN) return DEMANDA_MIN;
        else return valor;
    }
    private enum OpcionsMenu {
        GESTIO_BARRES_CONTROL,
        GESTIO_REACTOR,
        GESTIO_SISTEMA_REFRIGERACIO,
        MOSTRAR_ESTAT_CENTRAL,
        MOSTRAR_BITACOLA,
        MOSTRAR_INCIDENCIES,
        MOSTRAR_DEMANDA_SATISFETA,
        FINALITZAR_DIA,
        GUARDAR_DADES,
        CARREGAR_DADES,
        SORTIR
    }

    private enum OpcionsBarresControl {
        MOSTRAR_INSERCIO,
        ESTABLIR_INSERCIO,
        SORTIR
    }

    private enum OpcionsReactor {
        ACTIVAR,
        DESACTIVAR,
        MOSTRAR_ESTAT,
        SORTIR
    }

    private enum OpcionsSistemaRefrigeracio {
        ACTIVAR_TOTES,
        DESACTIVAR_TOTES,
        ACTIVAR_BOMBA,
        DESACTIVAR_BOMBA,
        MOSTRAR_ESTAT,
        SORTIR
    }
}
