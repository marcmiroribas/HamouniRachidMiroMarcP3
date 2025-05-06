package prog2.model;



public class PaginaBitacola  {
    private int dia;

    public PaginaBitacola(int dia) {
        this.dia = dia;
    }

    public int getDia() {
        return dia;
    }

    @Override
    public String toString() {
        return "Dia: " + dia;
    }
}
