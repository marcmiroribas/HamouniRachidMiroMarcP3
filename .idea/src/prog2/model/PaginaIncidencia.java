package prog2.model;

import java.util.ArrayList;

public class PaginaIncidencia extends PaginaBitacola{

    private ArrayList<String> incidencies;

    public void afegeixIncidencia(String descIncidencia) {
        incidencies.add(descIncidencia);

    }

    @Override
    public String toString() {
        return "# Pàgina Estat\n" + "- Dia: " + this.dia + "\n - Descripció Incidencia: ";
    }
}
