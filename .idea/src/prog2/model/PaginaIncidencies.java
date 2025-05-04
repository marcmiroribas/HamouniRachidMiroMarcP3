package prog2.model;

import java.util.ArrayList;
import java.util.Iterator;

public class PaginaIncidencies extends PaginaBitacola{

    private ArrayList<String> incidencies;

    public PaginaIncidencies(int dia) {
        super(dia);
        this.incidencies = new ArrayList<>();
    }

    public void afegeixIncidencia(String descIncidencia) {
        incidencies.add(descIncidencia);

    }


    public String toString() {
        String resultat = "# Pàgina Incidències\n" +
                "- Dia: " + getDia() + "\n";

        Iterator<String> it = incidencies.iterator();
        while (it.hasNext()) {
            String incidencia = it.next();
            resultat += "- Descripció Incidència: " + incidencia + "\n";
        }

        return resultat;
    }
}
