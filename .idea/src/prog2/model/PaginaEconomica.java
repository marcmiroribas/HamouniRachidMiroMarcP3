package prog2.model;

public class PaginaEconomica extends PaginaBitacola {
    private float demandaPotencia;
    private float potenciaGenerada;
    private float percentatgeSatisfet;
    private float beneficis;
    private float penalitzacioExces;
    private float costOperatiu;
    private float guanys;

    public PaginaEconomica(int dia, float demandaPotencia, float potenciaGenerada,
                          float percentatgeSatisfet, float beneficis, float penalitzacioExces,
                          float costOperatiu, float guanys) {
        super(dia);
        this.demandaPotencia = demandaPotencia;
        this.potenciaGenerada = potenciaGenerada;
        this.percentatgeSatisfet = percentatgeSatisfet;
        this.beneficis = beneficis;
        this.penalitzacioExces = penalitzacioExces;
        this.costOperatiu = costOperatiu;
        this.guanys = guanys;
    }
    public float getDemandaPotencia() {
        return demandaPotencia;
    }

    public float getPotenciaGenerada() {
        return potenciaGenerada;
    }

    public void setPotenciaGenerada(float potenciaGenerada) {
        this.potenciaGenerada = potenciaGenerada;
    }

    public float getPercentatgeSatisfet() {
        return percentatgeSatisfet;
    }

    public void setPercentatgeSatisfet(float percentatgeSatisfet) {
        this.percentatgeSatisfet = percentatgeSatisfet;
    }

    public float getBeneficis() {
        return beneficis;
    }

    public void setBeneficis(float beneficis) {
        this.beneficis = beneficis;
    }

    public void setDemandaPotencia(float demandaPotencia) {
        this.demandaPotencia = demandaPotencia;
    }


    public float getPenalitzacioExces() {
        return penalitzacioExces;
    }

    public void setPenalitzacioExces(float penalitzacioExces) {
        this.penalitzacioExces = penalitzacioExces;
    }

    public float getCostOperatiu() {
        return costOperatiu;
    }

    public void setCostOperatiu(float costOperatiu) {
        this.costOperatiu = costOperatiu;
    }

    public float getGuanys() {
        return guanys;
    }

    public void setGuanys(float guanys) {
        this.guanys = guanys;
    }

    public String toString() {
        String resultat = "# Pagina Econòmica\n" +
                "Dia: " + getDia() + "\n" +
                "Demanda de Potència: " + getDemandaPotencia() + "\n" +
                "Potência Generada: " + getPotenciaGenerada() + "\n" +
                "Demanda de Potència Satisfeta: " + getPercentatgeSatisfet() + "%\n" +
                "Beneficis: " + getBeneficis() + " Unitats Econòmiques\n" +
                "Penalització Excés Producció: " + getPenalitzacioExces() + " Unitats Econòmiques\n" +
                "Cost Operatiu: " + getCostOperatiu() + " Unitats Econòmiques\n" +
                "Guanys acumulats: " + getGuanys() + " Unitats Econòmiques\n";
        return resultat;

    }
}
