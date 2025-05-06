package prog2.model;

public class PaginaEstat extends PaginaBitacola{
    private int dia;
    private float grau;
    private float outReactor;
    private float outRefrigeracio;
    private float outGenerador;
    private float outTurbina;

    public PaginaEstat(int dia, float grau, float outReactor, float outRefrigeracio,
                       float outGenerador, float outTurbina) {
        super(dia);
        this.grau = grau;
        this.outReactor = outReactor;
        this.outRefrigeracio = outRefrigeracio;
        this.outGenerador = outGenerador;
        this.outTurbina = outTurbina;
    }
    @Override
    public int getDia() {
        return super.getDia();
    }


    public float getGrau() {
        return grau;
    }

    public void setGrau(float grau) {
        this.grau = grau;
    }

    public float getOutReactor() {
        return outReactor;
    }

    public void setOutReactor(float outReactor) {
        this.outReactor = outReactor;
    }

    public float getOutGenerador() {
        return outGenerador;
    }

    public void setOutGenerador(float outGenerador) {
        this.outGenerador = outGenerador;
    }

    public float getOutRefrigeracio() {
        return outRefrigeracio;
    }

    public void setOutRefrigeracio(float outRefrigeracio) {
        this.outRefrigeracio = outRefrigeracio;
    }

    public float getOutTurbina() {
        return outTurbina;
    }

    public void setOutTurbina(float outTurbina) {
        this.outTurbina = outTurbina;
    }

    @Override
    public String toString() {
        String resultat = "# Pàgina Estat\n" +
                "Dia: " + super.getDia() + "\n" +
                "Inserció Barres: " + String.format("%.1f", grau) + " %\n" +
                "Output Reactor: " + String.format("%.1f", outReactor) + " Graus\n" +
                "Output Sistema de Refrigeració: " + String.format("%.1f", outRefrigeracio) + " Graus\n" +
                "Output Generador de Vapor: " + String.format("%.1f", outGenerador) + " Graus\n" +
                "Output Turbina: " + String.format("%.1f", outTurbina) + " Unitats de Potència\n";
        return resultat;
    }
}

