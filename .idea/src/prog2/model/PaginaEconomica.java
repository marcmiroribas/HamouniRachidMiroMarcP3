package prog2.model;

public class PaginaEconomica extends PaginaBitacola{
    private float demandaPotencia;
    private float potenciaGenerada;
    private float percentatgeSatisfet;
    private float beneficis;
    private float penalitzacioExces;
    private float costOperatiu;
    private float guanys;

    public float getDemandaPotencia() {
        return demandaPotencia;
    }

    public float getPotenciaGenerada() {
        return potenciaGenerada;
    }

    public float getPercentatgeSatisfet() {
        return percentatgeSatisfet;
    }

    public float getBeneficis() {
        return beneficis;
    }


    public void setDemandaPotencia(float demandaPotencia) {
        this.demandaPotencia = demandaPotencia;
    }
}
