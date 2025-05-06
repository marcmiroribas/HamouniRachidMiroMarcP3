package prog2.model;
import prog2.vista.CentralUBException;

public class BombaRefrigerant implements InBombaRefrigerant{
    private int id;
    private boolean activada;
    private boolean foraDeServei;
    private VariableUniforme variableUniforme;
    private static final float CAP_REFRIGERACIO = 250;
    private static final float COST_OPERACIONAL = 130;


    public BombaRefrigerant(int id, VariableUniforme variableUniforme) {
        this.id = id;
        this.variableUniforme = variableUniforme;

    }

    /**
     * Retorna l'identificador numèric de la bomba refrigerant.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Activa la bomba refrigerant. El mètode llançarà una excepció si la bomba
     * està fora de servei i en aquest cas la bomba no es podrà activar.
     */
    @Override
    public void activa() throws CentralUBException {
        if(foraDeServei) {
            throw new CentralUBException("La bomba està fora de servei");
        }
        else {
            activada = true;
        }
    }
    /**
     * Desactiva la bomba refrigerant.
     */
    @Override
    public void desactiva() {
        activada = false;
    }

    /**
     * Retorna true si la bomba refrigerant està activada.
     */
    @Override
    public boolean getActivat() {
        return activada;
    }

    /**
     * Revisa la bomba refrigerant. Es farà servir l'objecte de tipus
     * VariableUniforme per determinar si la bomba es queda fora de servei. En
     * cas afirmatiu, s'haurà de registrar la situació dins d'una pàgina
     * d'incidències.
     * @param p Objecte de tipus PaginaIncidencies per a registrar si la bomba
     * es queda fora de servei.
     */


    @Override
    public void revisa(PaginaIncidencies p) {
        if (variableUniforme.seguentValor() < 0.25f) { // 25% probabilidad
            this.foraDeServei = true;
            this.activada = false; // Se desactiva automáticamente
            p.afegeixIncidencia("La bomba refrigerant " + id + " està fora de servei");
        }
    }

    /**
     * Retorna true si la bomba refrigerant està fora de servei.
     */
    @Override
    public boolean getForaDeServei() {
        return foraDeServei;
    }
    /**
     * Retorna la capacitat de refrigeració en graus. Si la bomba no
     * està activada, retorna zero.
     */
    @Override
    public float getCapacitat() {
        if(!activada) {
            return 0.0f;
        }
        else {
        return CAP_REFRIGERACIO;
        }

    }

    /**
     * Retorna el cost operatiu de la bomba. Si la bomba no
     * està activada, retorna zero.
     */
    @Override
    public float getCostOperatiu() {
        if(!activada) {
            return 0.0f;
        }
        else {
            return COST_OPERACIONAL;
        }
    }

    @Override
    public String toString() {
        return "Id="+id+", Activat="+activada+", Fora de servei="+foraDeServei;
    }
}
