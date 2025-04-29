package prog2.model;

public class BombaRefrigerant implements InBombaRefrigerant{
    private int id;
    private boolean activada;
    private boolean foraDeServei;


    public BombaRefrigerant(int id, VariableUniforme variableUniforme) {
        this.id = id;

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
            throw new CentralUBException;
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

        }

    }

    /**
     * Retorna el cost operatiu de la bomba. Si la bomba no
     * està activada, retorna zero.
     */
    @Override
    public float getCostOperatiu() {
        if(activada==false) {
            return 0.0f;
        }
    }

    @Override
    public String toString() {
        return "Id="+id+", Activat="+activada+", Fora de servei="+foraDeServei;
    }
}
